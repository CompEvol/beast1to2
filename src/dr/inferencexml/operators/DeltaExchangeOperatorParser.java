/*
 * DeltaExchangeOperatorParser.java
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

import beast.core.parameter.IntegerParameter;
import beast.core.parameter.Parameter;
import beast.core.parameter.RealParameter;
import beast.evolution.operators.DeltaExchangeOperator;
import dr.inference.operators.CoercableMCMCOperator;
import dr.inference.operators.MCMCOperator;
import dr.xml.*;

/**
 */
public class DeltaExchangeOperatorParser extends AbstractXMLObjectParser {

    public static final String DELTA_EXCHANGE = "deltaExchange";
    public static final String DELTA = "delta";
    public static final String INTEGER_OPERATOR = "integer";
    public static final String PARAMETER_WEIGHTS = "parameterWeights";

    @Override
	public String getParserName() {
        return DELTA_EXCHANGE;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        CoercionMode mode = CoercionMode.parseMode(xo);

        final boolean isIntegerOperator = xo.getAttribute(INTEGER_OPERATOR, false);

        double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);
        double delta = xo.getDoubleAttribute(DELTA);

        if (delta <= 0.0) {
            throw new XMLParseException("delta must be greater than 0.0");
        }

        Parameter<?> parameter = (Parameter<?>) xo.getChild(Parameter.class);


        Integer[] weights = null;
        if (xo.hasAttribute(PARAMETER_WEIGHTS)) {
            int[] parameterWeights;
            parameterWeights = xo.getIntegerArrayAttribute(PARAMETER_WEIGHTS);
            System.out.print("RealParameter weights for delta exchange are: ");
            for (int parameterWeight : parameterWeights) {
                System.out.print(parameterWeight + "\t");
            }
            System.out.println();

            if (parameterWeights.length != parameter.getDimension()) {
                throw new XMLParseException("parameter weights have the same length as parameter");
            }
//        } else {
//            parameterWeights = new int[parameter.getDimension()];
//            for (int i = 0; i < parameterWeights.length; i++) {
//                parameterWeights[i] = 1;
//            }
            weights = new Integer[parameterWeights.length];
            for (int i  = 0; i < weights.length; i++) {
            	weights[i] = parameterWeights[i];
            }
        }

        DeltaExchangeOperator operator = new DeltaExchangeOperator();
        operator.initByName("weight", weight,
        		(parameter instanceof RealParameter ? "parameter" : "intparameter"), parameter,
        		"weightvector", (weights == null ? null : new IntegerParameter(weights)),
        		"delta", delta,
        		"autoOptimize", mode.equals(CoercionMode.COERCION_ON),
        		"integer", isIntegerOperator);

        return operator;
  	}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element returns a delta exchange operator on a given parameter.";
    }

    @Override
	public Class getReturnType() {
        return beast.evolution.operators.DeltaExchangeOperator.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newDoubleRule(DELTA),
            AttributeRule.newIntegerArrayRule(PARAMETER_WEIGHTS, true),
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
            AttributeRule.newBooleanRule(CoercableMCMCOperator.AUTO_OPTIMIZE, true),
            AttributeRule.newBooleanRule(INTEGER_OPERATOR, true),
            new ElementRule(RealParameter.class)
    };
}
