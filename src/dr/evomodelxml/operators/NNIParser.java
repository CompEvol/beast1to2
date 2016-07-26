/*
 * NNIParser.java
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

import dr.evomodel.operators.NNI;
import beast.evolution.tree.Tree;
import dr.inference.operators.MCMCOperator;
import dr.xml.*;

/**
 */
public class NNIParser extends AbstractXMLObjectParser {

    public static final String NNI = "NearestNeighborInterchange";

    @Override
	public String getParserName() {
        return NNI;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Tree treeModel = (Tree) xo.getChild(Tree.class);
        double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);

        return new NNI(treeModel, weight);
    */
		}

    // ************************************************************************
    // AbstractXMLObjectParser
    // implementation
    // ************************************************************************

    @Override
	public String getParserDescription() {
        return "This element represents a NNI operator. "
                + "This operator swaps a random subtree with its uncle.";
    }

    @Override
	public Class getReturnType() {
        return NNI.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
            new ElementRule(Tree.class)
    };
}
