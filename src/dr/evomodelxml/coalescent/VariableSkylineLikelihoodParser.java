/*
 * VariableSkylineLikelihoodParser.java
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

import dr.evomodel.coalescent.VariableSkylineLikelihood;
import beast.evolution.tree.Tree;
import beast.core.parameter.RealParameter;
import dr.xml.*;


/**
 */
public class VariableSkylineLikelihoodParser extends AbstractXMLObjectParser {

    public static final String SKYLINE_LIKELIHOOD = "ovariableSkyLineLikelihood";
    public static final String POPULATION_SIZES = "populationSizes";
    public static final String INDICATOR_PARAMETER = "indicators";
    public static final String LOG_SPACE = "logUnits";

    public static final String TYPE = "type";
    public static final String STEPWISE = "stepwise";
    public static final String LINEAR = "linear";
    public static final String EXPONENTIAL = "exponential";

    @Override
	public String getParserName() {
        return SKYLINE_LIKELIHOOD;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        XMLObject cxo = xo.getChild(POPULATION_SIZES);
        RealParameter param = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(INDICATOR_PARAMETER);
        RealParameter param2 = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(CoalescentLikelihoodParser.POPULATION_TREE);
        Tree treeModel = (Tree) cxo.getChild(Tree.class);

        VariableSkylineLikelihood.Type type = VariableSkylineLikelihood.Type.STEPWISE;
        /* if (xo.hasAttribute(LINEAR) && !xo.getBooleanAttribute(LINEAR))
        {
            type = VariableSkylineLikelihood.Type.STEPWISE;
        }* /

        if (xo.hasAttribute(TYPE)) {
            final String s = xo.getStringAttribute(TYPE);
            if (s.equalsIgnoreCase(STEPWISE)) {
                type = VariableSkylineLikelihood.Type.STEPWISE;
            } else if (s.equalsIgnoreCase(LINEAR)) {
                type = VariableSkylineLikelihood.Type.LINEAR;
            } else if (s.equalsIgnoreCase(EXPONENTIAL)) {
                type = VariableSkylineLikelihood.Type.EXPONENTIAL;
            } else {
                throw new XMLParseException("Unknown Bayesian Skyline type: " + s);
            }
        }

        boolean logSpace = xo.getBooleanAttribute(LOG_SPACE);

        Logger.getLogger("dr.evomodel").info("Variable skyline plot: " + type.toString() + " control points");

        return new VariableSkylineLikelihood(treeModel, param, param2, type, logSpace);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element represents the likelihood of the tree given the population size vector.";
    }

    @Override
	public Class getReturnType() {
        return VariableSkylineLikelihood.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newBooleanRule(TYPE, true),
            new ElementRule(POPULATION_SIZES, new XMLSyntaxRule[]{
                    new ElementRule(RealParameter.class)
            }),
            new ElementRule(INDICATOR_PARAMETER, new XMLSyntaxRule[]{
                    new ElementRule(RealParameter.class)
            }),
            new ElementRule(CoalescentLikelihoodParser.POPULATION_TREE, new XMLSyntaxRule[]{
                    new ElementRule(Tree.class)
            }),
            AttributeRule.newBooleanRule(LOG_SPACE)
    };

}
