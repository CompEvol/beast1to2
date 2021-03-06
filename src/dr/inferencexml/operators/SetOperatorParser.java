/*
 * SetOperatorParser.java
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

import beast.core.parameter.RealParameter;
import dr.inference.operators.MCMCOperator;
import dr.inference.operators.SetOperator;
import dr.xml.*;

/**
 */
public class SetOperatorParser extends AbstractXMLObjectParser {

    public static final String SET_OPERATOR = "setOperator";
    public static final String SET = "set";

    @Override
	public String getParserName() {
        return SET_OPERATOR;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        double[] values = xo.getDoubleArrayAttribute(SET);
        double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);

        RealParameter parameter = (RealParameter) xo.getChild(RealParameter.class);

        System.out.println("Creating set operator for parameter " + parameter.getParameterName());
        System.out.print("  set = {" + values[0]);
        for (int i = 1; i < values.length; i++) {
            System.out.print(", " + values[i]);
        }
        System.out.println("}");

        SetOperator operator = new SetOperator(parameter, values);
        operator.setWeight(weight);

        return operator;
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element represents an operator on a set.";
    }

    @Override
	public Class getReturnType() {
        return SetOperator.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            AttributeRule.newDoubleArrayRule(SET),
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
            new ElementRule(RealParameter.class)
    };
}
