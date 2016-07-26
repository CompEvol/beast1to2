/*
 * MaskFlipOperatorParser.java
 *
 * Copyright (c) 2002-2016 Alexei Drummond, Andrew Rambaut and Marc Suchard
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
import dr.inference.operators.MaskMoveOperator;
import dr.xml.*;

/**
 *
 */
public class MaskMoveOperatorParser extends AbstractXMLObjectParser {

    public static final String MASK_FLIP_OPERATOR = "maskMoveOperator";
    public static final String CUT_POINT = "cutPoint";
    public static final String CUT_MASK = "cutMask";
    public static final String SELECT_BEFORE = "selectBefore";
    public static final String SELECT_AFTER = "selectAfter";
//    public static final String BEFORE_VALUE = "before";
//    public static final String AFTER_VALUE = "after";

    @Override
	public String getParserName() {
        return MASK_FLIP_OPERATOR;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);

//        RealParameter parameter = (RealParameter) xo.getChild(RealParameter.class);

        List<RealParameter> masks = new ArrayList<RealParameter>();
        for (int i = 0; i < xo.getChildCount(); ++i) {
            if (xo.getChild(i) instanceof RealParameter) {
                masks.add((RealParameter) xo.getChild(i));
            }
        }

        RealParameter cutPoint = (RealParameter) xo.getElementFirstChild(CUT_POINT);
//        RealParameter before = (RealParameter) xo.getElementFirstChild(BEFORE_VALUE);
//        RealParameter after = (RealParameter) xo.getElementFirstChild(AFTER_VALUE);

        double[] before = xo.getChild(CUT_MASK).getDoubleArrayAttribute(SELECT_BEFORE);
        double[] after = xo.getChild(CUT_MASK).getDoubleArrayAttribute(SELECT_AFTER);

        int[] beforeList = new int[before.length];
        for (int i = 0; i < before.length; ++i) {
            beforeList[i] = ((int) (before[i] -1.0 + 0.5)); // Switch to 0-index
        }

        int[] afterList = new int[after.length];
        for (int i = 0; i < before.length; ++i) {
            afterList[i] = ((int) (after[i] -1.0 + 0.5));
        }

        if (beforeList.length != afterList.length) {
            throw new XMLParseException("selectBefore length != selectAfter length");
        }


        if (!MaskMoveOperator.checkMaskValues(masks, cutPoint, beforeList, afterList)) {
            String name = xo.hasId() ? xo.getId() : "null";
            throw new XMLParseException("Bad initialization parameter values in " + name);
        }

        return new MaskMoveOperator(masks, cutPoint, beforeList, afterList, weight);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element returns a mask-flip operator on a set of given parameters.";
    }

    @Override
	public Class getReturnType() {
        return MaskMoveOperator.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
            new ElementRule(RealParameter.class, 1, Integer.MAX_VALUE),
            new ElementRule(CUT_POINT, new XMLSyntaxRule[] {
                   new ElementRule(RealParameter.class),
            }),
            new ElementRule(CUT_MASK, new XMLSyntaxRule[] {
                    AttributeRule.newDoubleArrayRule(SELECT_BEFORE),
                    AttributeRule.newDoubleArrayRule(SELECT_AFTER),
            })
//            new ElementRule(BEFORE_VALUE, new XMLSyntaxRule[] {
//                   new ElementRule(RealParameter.class),
//            }),
//            new ElementRule(AFTER_VALUE, new XMLSyntaxRule[] {
//                   new ElementRule(RealParameter.class),
//            }),
    };
}
