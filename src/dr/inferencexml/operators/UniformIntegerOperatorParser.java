/*
 * UniformIntegerOperatorParser.java
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

import beast.core.BEASTObject;
import beast.core.parameter.IntegerParameter;
import beast.core.parameter.Parameter;
import beast.evolution.operators.UniformOperator;
import dr.inference.operators.MCMCOperator;
import dr.xml.*;

/**
 */
public class UniformIntegerOperatorParser extends AbstractXMLObjectParser {

    public final static String UNIFORM_INTEGER_OPERATOR = "uniformIntegerOperator";

    @Override
	public String getParserName() {
        return UNIFORM_INTEGER_OPERATOR;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);

        Parameter parameter = (Parameter) xo.getChild(Parameter.class);

        int count = 1;
        if (xo.hasAttribute("count")) count = xo.getIntegerAttribute("count");

        if (parameter instanceof IntegerParameter) {
            int lower = (int) ((IntegerParameter) parameter).getLower();
            if (xo.hasAttribute("lower")) lower = xo.getIntegerAttribute("lower");

            int upper = (int) ((IntegerParameter) parameter).getUpper();
            if (xo.hasAttribute("upper")) upper = xo.getIntegerAttribute("upper");

            if (upper == lower || lower == (int) Double.NEGATIVE_INFINITY || upper == (int) Double.POSITIVE_INFINITY) {
                throw new XMLParseException(this.getParserName() + " boundaries not found in parameter "
                        + ((parameter.getID() + " Use operator lower and upper !")));
            }
            ((IntegerParameter) parameter).lowerValueInput.setValue(lower, (BEASTObject) parameter);
            ((IntegerParameter) parameter).upperValueInput.setValue(upper, (BEASTObject) parameter);
            
            UniformOperator operator = new UniformOperator();
            operator.initByName("weight", weight, "howMany", count, "parameter", parameter);
            return operator;
        } else { // Variable<Integer>, Bounds.Staircase
            UniformOperator operator = new UniformOperator();
            operator.initByName("weight", weight, "howMany", count, "parameter", parameter);
            return operator;
        }
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
        return UniformOperator.class;
    }


    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
            AttributeRule.newDoubleRule("upper", true),
            AttributeRule.newDoubleRule("lower", true),
            AttributeRule.newDoubleRule("count", true),
            new ElementRule(Parameter.class)
    };
}
