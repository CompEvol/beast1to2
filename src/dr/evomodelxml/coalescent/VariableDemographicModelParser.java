/*
 * VariableDemographicModelParser.java
 *
 * Copyright (c) 2002-2015 Alexei Drummond, Andrew Rambaut and Marc Suchard
 *
 * This file is part of BEAST.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * BEAST is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 *  BEAST is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with BEAST; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package dr.evomodelxml.coalescent;

import dr.xml.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import beast.core.parameter.BooleanParameter;
import beast.core.parameter.IntegerParameter;
import beast.core.parameter.Parameter;
import beast.core.parameter.RealParameter;
import beast.evolution.tree.Tree;
import beast.evolution.tree.coalescent.Coalescent;
import beast.evolution.tree.coalescent.CompoundPopulationFunction;
import beast.evolution.tree.coalescent.PopulationFunction;
import beast.evolution.tree.coalescent.ScaledPopulationFunction;
import beast.evolution.tree.coalescent.TreeIntervals;
import beast1to2.Beast1to2Converter;

/**
 */
public class VariableDemographicModelParser extends AbstractXMLObjectParser {
    public static final String MODEL_NAME = "variableDemographic";
    public static final String POPULATION_SIZES = "populationSizes";
    public static final String INDICATOR_PARAMETER = "indicators";
    public static final String POPULATION_TREES = "trees";
    private static final String PLOIDY = "ploidy";
    public static final String POP_TREE = "ptree";

    public static final String LOG_SPACE = "logUnits";
    public static final String USE_MIDPOINTS = "useMidpoints";

    public static final String TYPE = "type";
    //public static final String STEPWISE = "stepwise";
    //public static final String LINEAR = "linear";
    //public static final String EXPONENTIAL = "exponential";

    public static final String demoElementName = "demographic";

    public String getParserName() {
        return MODEL_NAME;
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        XMLObject cxo = xo.getChild(POPULATION_SIZES);
        RealParameter popParam = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(INDICATOR_PARAMETER);
        Parameter indicatorParamX = (Parameter) cxo.getChild(Parameter.class);
        // convert to BooleanParameter
        BooleanParameter indicatorParam = null;
        for (int i = 0; i < xo.getChildCount(); i++) {
        	Object o = xo.getRawChild(i); 
        	if (o instanceof XMLObject) {
        		XMLObject xco = (XMLObject) o;
        		if (xco.getName().equals(INDICATOR_PARAMETER)) {
        			o = xco.getRawChild(0);
        			indicatorParam = new BooleanParameter();
        			indicatorParam.initByName("dimension", indicatorParamX.getDimension(), "value", "1");
        			((XMLObject)o).setNativeObject(indicatorParam);
        		}
        	}
        }

        cxo = xo.getChild(POPULATION_TREES);

        final int nc = cxo.getChildCount();
        Tree[] treeModels = new Tree[nc];
        Double[] populationFactor = new Double[nc];

        for (int k = 0; k < treeModels.length; ++k) {
            final XMLObject child = (XMLObject) cxo.getChild(k);
            populationFactor[k] = child.hasAttribute(PLOIDY) ? child.getDoubleAttribute(PLOIDY) : 1.0;

            treeModels[k] = (Tree) child.getChild(Tree.class);
        }

        CompoundPopulationFunction.Type type = CompoundPopulationFunction.Type.STEPWISE;

        if (xo.hasAttribute(TYPE)) {
            final String s = xo.getStringAttribute(TYPE);
            if (s.equalsIgnoreCase(CompoundPopulationFunction.Type.STEPWISE.toString())) {
                type = CompoundPopulationFunction.Type.STEPWISE;
            } else if (s.equalsIgnoreCase(CompoundPopulationFunction.Type.LINEAR.toString())) {
                type = CompoundPopulationFunction.Type.LINEAR;
//            } else if (s.equalsIgnoreCase(CompoundPopulationFunction.Type.EXPONENTIAL.toString())) {
//                type = CompoundPopulationFunction.Type.EXPONENTIAL;
            } else {
                throw new XMLParseException("Unknown Bayesian Skyline type: " + s);
            }
        }

        final boolean logSpace = xo.getAttribute(LOG_SPACE, false);// || type == CompoundPopulationFunction.Type.EXPONENTIAL;
        if (logSpace == true) {
        	throw new XMLParseException(Beast1to2Converter.NIY + " variableDemographic/" + LOG_SPACE + " is not supported");
        }
        final boolean useMid = xo.getAttribute(USE_MIDPOINTS, false);

        Logger.getLogger("dr.evomodel").info("Variable demographic: " + type.toString() + " control points");
        
        List<TreeIntervals> intervals = new ArrayList<>();
        for (Tree tree : treeModels) {
        	intervals.add(new TreeIntervals(tree));
        }
        
        RealParameter factor = new RealParameter(populationFactor);
        
        CompoundPopulationFunction popFun = new CompoundPopulationFunction();
        popFun.setID("demographic.alltrees");
        popFun.initByName("itree", intervals, "populationIndicators", indicatorParam, "populationSizes", popParam, 
        		"type", type.toString(), "useIntervalsMiddle", useMid);

        ScaledPopulationFunction scaledPopFun = new ScaledPopulationFunction();
        scaledPopFun.setID("scaled.demographic.alltrees");
        scaledPopFun.initByName("population", popFun, "factor", factor);
        return scaledPopFun;
        
//        Coalescent likelihood = new Coalescent();
//        likelihood.initByName("treeIntervals", intervals.get(0), "populationModel", scaledPopFun);
//        return likelihood;
 	}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public String getParserDescription() {
        return "This element represents the likelihood of the tree given the population size vector.";
    }

    public Class getReturnType() {
        return PopulationFunction.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newStringRule(TYPE, true),
            AttributeRule.newBooleanRule(LOG_SPACE, true),
            AttributeRule.newBooleanRule(USE_MIDPOINTS, true),

            new ElementRule(POPULATION_SIZES, new XMLSyntaxRule[]{
                    new ElementRule(RealParameter.class)
            }),
            new ElementRule(INDICATOR_PARAMETER, new XMLSyntaxRule[]{
                    new ElementRule(Parameter.class)
            }),
            new ElementRule(POPULATION_TREES, new XMLSyntaxRule[]{
                    new ElementRule(POP_TREE, new XMLSyntaxRule[]{
                            AttributeRule.newDoubleRule(PLOIDY, true),
                            new ElementRule(Tree.class),
                    }, 1, Integer.MAX_VALUE)
            })
    };
}
