/*
 * BayesianSkylineLikelihoodParser.java
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

import java.util.logging.Logger;

import beast.core.parameter.BooleanParameter;
import beast.core.parameter.IntegerParameter;
import beast.core.parameter.Parameter;
import beast.core.parameter.RealParameter;
import beast.core.util.Log;
import beast.evolution.tree.Tree;
import beast.evolution.tree.coalescent.BayesianSkyline;
import beast.evolution.tree.coalescent.TreeIntervals;

/**
 */
public class BayesianSkylineLikelihoodParser extends AbstractXMLObjectParser {

    public static final String SKYLINE_LIKELIHOOD = "generalizedSkyLineLikelihood";
    public static final String POPULATION_SIZES = "populationSizes";
    public static final String GROUP_SIZES = "groupSizes";

    public static final String TYPE = "type";
    public static final String STEPWISE = "stepwise";
    public static final String LINEAR = "linear";
    public static final String EXPONENTIAL = "exponential";

    public String getParserName() {
        return SKYLINE_LIKELIHOOD;
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        XMLObject cxo = xo.getChild(POPULATION_SIZES);
        RealParameter param = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(GROUP_SIZES);
        RealParameter param2x = (RealParameter) cxo.getChild(RealParameter.class);
        // convert to IntegerParameter
        IntegerParameter groupSizes = null;
        for (int i = 0; i < xo.getChildCount(); i++) {
        	Object o = xo.getRawChild(i); 
        	if (o instanceof XMLObject) {
        		XMLObject xco = (XMLObject) o;
        		if (xco.getName().equals(GROUP_SIZES)) {
        			o = xco.getRawChild(0);
        			groupSizes = new IntegerParameter();
        			groupSizes.initByName("dimension", param2x.getDimension(), "value", "1");
        			((XMLObject)o).setNativeObject(groupSizes);
        		}
        	}
        }


        cxo = xo.getChild(CoalescentLikelihoodParser.POPULATION_TREE);
        Tree treeModel = (Tree) cxo.getChild(Tree.class);

        //int type = BayesianSkyline.LINEAR_TYPE;
        String typeName = LINEAR;
        if (xo.hasAttribute(LINEAR) && !xo.getBooleanAttribute(LINEAR)) {
            //type = BayesianSkyline.STEPWISE_TYPE;
            typeName = STEPWISE;
        }

        if (xo.hasAttribute(TYPE)) {
            if (xo.getStringAttribute(TYPE).equalsIgnoreCase(STEPWISE)) {
                //type = BayesianSkyline.STEPWISE_TYPE;
                typeName = STEPWISE;
            } else if (xo.getStringAttribute(TYPE).equalsIgnoreCase(LINEAR)) {
                //type = BayesianSkyline.LINEAR_TYPE;
                typeName = LINEAR;
            } else if (xo.getStringAttribute(TYPE).equalsIgnoreCase(EXPONENTIAL)) {
                //type = BayesianSkyline.EXPONENTIAL_TYPE;
                typeName = EXPONENTIAL;
            } else throw new XMLParseException("Unknown Bayesian Skyline type: " + xo.getStringAttribute(TYPE));
        }
        
        if (!typeName.equals(STEPWISE)) {
        	Log.warning.println("BayesianSkyline only supports stepwise, not " + typeName);
        	Log.warning.println("Changing type to stepwise");
        }

        if (groupSizes.getDimension() > (treeModel.getLeafNodeCount()-1)) {
            throw new XMLParseException("There are more groups (" + groupSizes.getDimension()
                    + ") than coalescent nodes in the tree (" + (treeModel.getLeafNodeCount()-1) + ").");
        }

        Logger.getLogger("dr.evomodel").info("Bayesian skyline plot: " + param.getDimension() + " " + typeName + " control points");
        
        TreeIntervals intervals = new TreeIntervals(treeModel);
        BayesianSkyline likelihood = new BayesianSkyline();
        likelihood.initByName("treeIntervals", intervals, "popSizes", param, "groupSizes", groupSizes);

        return likelihood;
	}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public String getParserDescription() {
        return "This element represents the likelihood of the tree given the population size vector.";
    }

    public Class getReturnType() {
        return BayesianSkyline.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules;{
        rules = new XMLSyntaxRule[]{
                new XORRule(
                        AttributeRule.newBooleanRule(LINEAR),
                        AttributeRule.newStringRule(TYPE)
                ),
                new ElementRule(POPULATION_SIZES, new XMLSyntaxRule[]{
                        new ElementRule(RealParameter.class)
                }),
                new ElementRule(GROUP_SIZES, new XMLSyntaxRule[]{
                        new ElementRule(RealParameter.class)
                }),
                new ElementRule(CoalescentLikelihoodParser.POPULATION_TREE, new XMLSyntaxRule[]{
                        new ElementRule(Tree.class)
                }),
        };
    }
}
