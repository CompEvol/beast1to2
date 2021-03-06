/*
 * BitFlipOperatorParser.java
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


import beast.core.parameter.BooleanParameter;
import beast.core.util.Log;
import beast.evolution.operators.BitFlipOperator;
import beast.evolution.tree.Tree;
import beast1to2.Beast1to2Converter;
import dr.inference.operators.MCMCOperator;
import dr.xml.*;

/**
 *
 */
public class BitFlipOperatorParser extends AbstractXMLObjectParser {

    public static final String BIT_FLIP_OPERATOR = "bitFlipOperator";
    public static final String BITS = "bits";
    public static final String USES_SUM_PRIOR = "usesPriorOnSum";
    // public static final String FOR_DRIFT = "forDrift";

    @Override
	public String getParserName() {
        return BIT_FLIP_OPERATOR;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);

        BooleanParameter parameter = (BooleanParameter) xo.getChild(BooleanParameter.class);

        boolean usesPriorOnSum = xo.getAttribute(USES_SUM_PRIOR, true);

        // boolean forDrift = xo.getAttribute(FOR_DRIFT,false);

        Tree treeModel = (Tree) xo.getChild(Tree.class);
        if (treeModel != null) {
        	Log.warning.println(Beast1to2Converter.NIY + " tree is ignored");
        }
        BitFlipOperator operator = new BitFlipOperator();
        operator.initByName(MCMCOperator.WEIGHT, weight, "parameter", parameter, "uniform", usesPriorOnSum);

        return operator;
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element returns a bit-flip operator on a given parameter.";
    }

    @Override
	public Class getReturnType() {
        return BitFlipOperator.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
//                AttributeRule.newIntegerRule(BITS,true),
            AttributeRule.newBooleanRule(USES_SUM_PRIOR, true),
            //  AttributeRule.newBooleanRule(FOR_DRIFT,true),
            new ElementRule(Tree.class, true),
            new ElementRule(BooleanParameter.class)
    };

}
