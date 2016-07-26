/*
 * RandomWalkOperatorParser.java
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
import beast.core.parameter.Parameter;
import beast.core.parameter.RealParameter;
import beast.core.util.Log;
import beast.evolution.alignment.*;
import beast.evolution.operators.IntRandomWalkOperator;
import beast.evolution.operators.RealRandomWalkOperator;
import beast.evolution.operators.TipDatesRandomWalker;
import beast1to2.Beast1to2Converter;
import beast1to2.BeastParser;
import dr.inference.operators.CoercableMCMCOperator;
import dr.inference.operators.CoercionMode;
import dr.inference.operators.MCMCOperator;
import dr.inference.operators.RandomWalkOperator;
import dr.xml.*;

/**
 */
public class RandomWalkOperatorParser extends AbstractXMLObjectParser {

    public static final String RANDOM_WALK_OPERATOR = "randomWalkOperator";
    public static final String WINDOW_SIZE = "windowSize";
    public static final String UPDATE_INDEX = "updateIndex";
    public static final String UPPER = "upper";
    public static final String LOWER = "lower";

    public static final String BOUNDARY_CONDITION = "boundaryCondition";

        @Override
		public String getParserName() {
            return RANDOM_WALK_OPERATOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {

            CoercionMode mode = CoercionMode.parseMode(xo);

            double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);
            double windowSize = xo.getDoubleAttribute(WINDOW_SIZE);
            Parameter<?> parameter = (Parameter<?>) xo.getChild(Parameter.class);

            Double lower = null;
            Double upper = null;

            if (xo.hasAttribute(LOWER)) {
                lower = xo.getDoubleAttribute(LOWER);
            }

            if (xo.hasAttribute(UPPER)) {
                upper = xo.getDoubleAttribute(UPPER);
            }

            RandomWalkOperator.BoundaryCondition condition = RandomWalkOperator.BoundaryCondition.valueOf(
                    xo.getAttribute(BOUNDARY_CONDITION, RandomWalkOperator.BoundaryCondition.reflecting.name()));
            if (condition == RandomWalkOperator.BoundaryCondition.absorbing) {
            	Log.warning.println(Beast1to2Converter.NIY + " Attribute " + BOUNDARY_CONDITION + " is ignored");
            }
            
            Operator operator = null;
            if (BeastParser.leafParamToTreeMap.containsKey(parameter)) {
            	operator = new TipDatesRandomWalker();
            	TaxonSet taxonset = BeastParser.leafParamToTaxonSetMap.get(parameter);
                operator.initByName("weight", weight, "windowSize", windowSize,
                		"taxonset", taxonset,
                		"tree", BeastParser.leafParamToTreeMap.get(parameter));
                return operator;
            } else if (parameter instanceof RealParameter) {
	            operator = new RealRandomWalkOperator();
	            operator.initByName("weight", weight, "windowSize", windowSize,
	            		"parameter", parameter);
            } else {
	            operator = new IntRandomWalkOperator();
	            operator.initByName("weight", weight, "windowSize", windowSize,
	            		"parameter", parameter);
            	
            }
            
            if (xo.hasChildNamed(UPDATE_INDEX)) {
            	Log.warning.println(Beast1to2Converter.NIY + " Attribute " + UPDATE_INDEX + " is ignored");
//                XMLObject cxo = xo.getChild(UPDATE_INDEX);
//                Parameter updateIndex = (Parameter) cxo.getChild(Parameter.class);
//                if (updateIndex.getDimension() != parameter.getDimension())
//                    throw new RuntimeException("Parameter to update and missing indices must have the same dimension");
//                return new RandomWalkOperator(parameter, updateIndex, windowSize, condition,
//                        weight, mode, lower, upper);
            }

            return operator;
        }

        //************************************************************************
        // AbstractXMLObjectParser implementation
        //************************************************************************

        @Override
		public String getParserDescription() {
            return "This element returns a random walk operator on a given parameter.";
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
                AttributeRule.newDoubleRule(WINDOW_SIZE),
                AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
                AttributeRule.newDoubleRule(LOWER, true),
                AttributeRule.newDoubleRule(UPPER, true),
                AttributeRule.newBooleanRule(CoercableMCMCOperator.AUTO_OPTIMIZE, true),
                new ElementRule(UPDATE_INDEX,
                        new XMLSyntaxRule[] {
                                new ElementRule(Parameter.class),
                        },true),
                new StringAttributeRule(BOUNDARY_CONDITION, null, RandomWalkOperator.BoundaryCondition.values(), true),
                new ElementRule(Parameter.class)
        };
}
