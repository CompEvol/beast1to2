/*
 * RandomWalkIntegerSetSizeWeightedOperatorParser.java
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

package dr.evomodelxml.operators;

import dr.xml.*;
import dr.inference.operators.MCMCOperator;
import dr.evomodel.operators.RandomWalkIntegerSetSizeWeightedOperator;
import beast.core.parameter.RealParameter;
import dr.evomodel.tree.MicrosatelliteSamplerTreeModel;

/**
 * @author Chieh-Hsi Wu
 *
 * This is the parser for the random walk integer set size weighted operator.
 */
public class RandomWalkIntegerSetSizeWeightedOperatorParser extends AbstractXMLObjectParser {

    public static final String RANDOM_WALK_INT_SET_SIZE_WGT_OP = "randomWalkIntegerSetSizeWeightedOperator";

    public static final String WINDOW_SIZE = "windowSize";
    public static final String BASE_SET_SIZE = "baseSetSize";

    @Override
	public String getParserName() {
        return RANDOM_WALK_INT_SET_SIZE_WGT_OP;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);

        double d = xo.getDoubleAttribute(WINDOW_SIZE);
        if (d != Math.floor(d)) {
            throw new XMLParseException("The window size of a " + RANDOM_WALK_INT_SET_SIZE_WGT_OP + " should be an integer");
        }

        double baseSetSize = xo.getDoubleAttribute(BASE_SET_SIZE);

        int windowSize = (int)d;
        RealParameter parameter = (RealParameter) xo.getChild(RealParameter.class);
        MicrosatelliteSamplerTreeModel msatSampleTreeModel = (MicrosatelliteSamplerTreeModel)xo.getChild(MicrosatelliteSamplerTreeModel.class);

        return new RandomWalkIntegerSetSizeWeightedOperator(parameter, windowSize, weight, msatSampleTreeModel, baseSetSize);
    */
		}

    @Override
	public String getParserDescription() {
        return "This element returns a random walk set size weighted operator on a given parameter.";
    }

    @Override
	public Class getReturnType() {
        return RandomWalkIntegerSetSizeWeightedOperator.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            AttributeRule.newDoubleRule(WINDOW_SIZE),
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
            AttributeRule.newDoubleRule(BASE_SET_SIZE),
            new ElementRule(RealParameter.class),
            new ElementRule(MicrosatelliteSamplerTreeModel.class)
    };
}