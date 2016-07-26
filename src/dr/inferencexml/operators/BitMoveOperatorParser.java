/*
 * BitMoveOperatorParser.java
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
import dr.inference.operators.BitMoveOperator;
import dr.inference.operators.MCMCOperator;
import dr.xml.*;

/**
 *
 */
public class BitMoveOperatorParser extends AbstractXMLObjectParser {

    public static final String BIT_MOVE_OPERATOR = "bitMoveOperator";
    public static final String NUM_BITS_TO_MOVE = "numBitsToMove";

    @Override
	public String getParserName() {
        return BIT_MOVE_OPERATOR;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);

        int numBitsToMove = xo.getIntegerAttribute(NUM_BITS_TO_MOVE);

        RealParameter bitsParameter = (RealParameter) xo.getElementFirstChild("bits");
        RealParameter valuesParameter = null;


        if (xo.hasChildNamed("values")) {
            valuesParameter = (RealParameter) xo.getElementFirstChild("values");
        }


        return new BitMoveOperator(bitsParameter, valuesParameter, numBitsToMove, weight);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element returns a bit-move operator on a given parameter.";
    }

    @Override
	public Class getReturnType() {
        return BitMoveOperator.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
            AttributeRule.newIntegerRule(NUM_BITS_TO_MOVE),
            new ElementRule("bits", RealParameter.class),
            new ElementRule("values", RealParameter.class, "values parameter", true)
    };

}
