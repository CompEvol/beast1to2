/*
 * UniformOperatorParser.java
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

package dr.inferencexml.operators;

import beast.core.Operator;
import beast.core.parameter.RealParameter;
import beast.evolution.operators.Uniform;
import beast.evolution.operators.UniformOperator;
import beast.evolution.tree.Tree;
import beast1to2.BeastParser;
import dr.xml.*;


public class UniformOperatorParser extends AbstractXMLObjectParser {
    public final static String UNIFORM_OPERATOR = "uniformOperator";
    public static final String LOWER = "lower";
    public static final String UPPER = "upper";

    @Override
	public String getParserName() {
        return UNIFORM_OPERATOR;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        double weight = xo.getDoubleAttribute(ScaleOperatorParser.WEIGHT);
        RealParameter parameter = (RealParameter) xo.getChild(RealParameter.class);

        if( parameter.getDimension() == 0 ) {
            throw new XMLParseException("parameter with 0 dimension.");
        }

        if (xo.hasAttribute(LOWER) || xo.hasAttribute(UPPER)) {
            throw new UnsupportedOperationException(getParserName() + " " +
                    LOWER + " and " + UPPER + " " + beast1to2.Beast1to2Converter.NIY);
        }

        if (BeastParser.internalParamToTreeMap.containsKey(parameter)) {
        	Tree tree = BeastParser.internalParamToTreeMap.get(parameter);
        	Uniform uniform = new Uniform();
        	uniform.initByName(ScaleOperatorParser.WEIGHT, weight, "tree", tree);
        	return uniform;
        } else {
	        UniformOperator uniformOperator = new UniformOperator();
	        uniformOperator.initByName(ScaleOperatorParser.WEIGHT, weight, "parameter", parameter);
	        return uniformOperator;
        }

		/*

        double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);
        RealParameter parameter = (RealParameter) xo.getChild(RealParameter.class);

        if( parameter.getDimension() == 0 ) {
             throw new XMLParseException("parameter with 0 dimension.");
        }

        Double lower = null;
        Double upper = null;

        if (xo.hasAttribute(LOWER)) {
            lower = xo.getDoubleAttribute(LOWER);
        }

        if (xo.hasAttribute(UPPER)) {
            upper = xo.getDoubleAttribute(UPPER);
        }       

        return new UniformOperator(parameter, weight, lower, upper);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "An operator that picks new parameter values uniformly at random.";
    }

    @Override
	public Class getReturnType() {
        return Operator.class;
    }


    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newDoubleRule(ScaleOperatorParser.WEIGHT),
            AttributeRule.newDoubleRule(LOWER, true),
            AttributeRule.newDoubleRule(UPPER, true),
            new ElementRule(RealParameter.class)
    };
}
