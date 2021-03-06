/*
 * AttributeBranchRateModelParser.java
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

package dr.evomodelxml.branchratemodel;

import dr.evomodel.branchratemodel.AttributeBranchRateModel;
import beast.evolution.tree.Tree;
import dr.xml.*;

/**
 */
public class AttributeBranchRateModelParser extends AbstractXMLObjectParser {

    public static final String RATE_ATTRIBUTE_NAME = "rateAttribute";

    @Override
	public String getParserName() {
        return AttributeBranchRateModel.ATTRIBUTE_BRANCH_RATE_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Tree tree = (Tree) xo.getChild(Tree.class);


        final String rateAttributeName = (xo.hasAttribute(RATE_ATTRIBUTE_NAME) ?
                xo.getStringAttribute(RATE_ATTRIBUTE_NAME) : null);

        return new AttributeBranchRateModel(tree, rateAttributeName);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element returns a branch rate model." +
                "The branch rates are specified by an attribute embedded in the nodes of the tree.";
    }

    @Override
	public Class getReturnType() {
        return AttributeBranchRateModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(Tree.class),
            new StringAttributeRule(RATE_ATTRIBUTE_NAME,
                    "Optional name of a rate attribute to be read with the trees")
    };


}
