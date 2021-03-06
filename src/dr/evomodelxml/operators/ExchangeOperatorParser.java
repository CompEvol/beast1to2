/*
 * ExchangeOperatorParser.java
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

import beast.evolution.operators.Exchange;
import beast.evolution.tree.Tree;
import dr.inferencexml.operators.ScaleOperatorParser;
import dr.xml.*;

/**
 */
public class ExchangeOperatorParser {

    public static final String NARROW_EXCHANGE = "narrowExchange";
    public static final String WIDE_EXCHANGE = "wideExchange";
    public static final String INTERMEDIATE_EXCHANGE = "intermediateExchange";

    public static XMLObjectParser NARROW_EXCHANGE_OPERATOR_PARSER = new AbstractXMLObjectParser() {
        @Override
		public String getParserName() {
            return NARROW_EXCHANGE;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		    final Tree treeModel = (Tree) xo.getChild(Tree.class);
            final double weight = xo.getDoubleAttribute(ScaleOperatorParser.WEIGHT);

            Exchange exchange = new Exchange();
            exchange.initByName(ScaleOperatorParser.WEIGHT, weight, "tree", treeModel);
            return exchange;
		}

        // ************************************************************************
        // AbstractXMLObjectParser implementation
        // ************************************************************************

        @Override
		public String getParserDescription() {
            return "This element represents a narrow exchange operator. "
                    + "This operator swaps a random subtree with its uncle.";
        }

        @Override
		public Class getReturnType() {
            return Exchange.class;
        }

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(ScaleOperatorParser.WEIGHT),
                new ElementRule(Tree.class)
        };
    };

    public static XMLObjectParser INTERMEDIATE_EXCHANGE_OPERATOR_PARSER = new AbstractXMLObjectParser() {
        @Override
		public String getParserName() {
            return INTERMEDIATE_EXCHANGE;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

            final Tree treeModel = (Tree) xo.getChild(Tree.class);
            final double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);
            return new ExchangeOperator(ExchangeOperator.INTERMEDIATE, treeModel, weight);
        */
		}

        // ************************************************************************
        // AbstractXMLObjectParser implementation
        // ************************************************************************

        @Override
		public String getParserDescription() {
            return "This element represents an intermediate exchange operator. "
                    + "This operator swaps two subtree random subtrees with a bias towards nearby subtrees.";
        }

        @Override
		public Class getReturnType() {
            return Exchange.class;
        }

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(ScaleOperatorParser.WEIGHT),
                new ElementRule(Tree.class)
        };
    };


    public static XMLObjectParser WIDE_EXCHANGE_OPERATOR_PARSER = new AbstractXMLObjectParser() {
        @Override
		public String getParserName() {
            return WIDE_EXCHANGE;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {
            final Tree treeModel = (Tree) xo.getChild(Tree.class);
            if (treeModel.getLeafNodeCount() <= 2) {
                throw new XMLParseException("Tree with fewer than 3 taxa");
            }
            final double weight = xo.getDoubleAttribute(ScaleOperatorParser.WEIGHT);

            Exchange exchange = new Exchange();
            exchange.initByName(ScaleOperatorParser.WEIGHT, weight, "tree", treeModel, "isNarrow", false);
            return exchange;
        }

        // ************************************************************************
        // AbstractXMLObjectParser implementation
        // ************************************************************************

        @Override
		public String getParserDescription() {
            return "This element represents a wide exchange operator. "
                    + "This operator swaps two random subtrees.";
        }

        @Override
		public Class getReturnType() {
            return Exchange.class;
        }

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules;{
            rules = new XMLSyntaxRule[]{
                    AttributeRule.newDoubleRule(ScaleOperatorParser.WEIGHT),
                    new ElementRule(Tree.class)
            };
        }
    };
}
