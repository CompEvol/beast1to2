/*
 * BranchingLikelihoodParser.java
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

package dr.evomodelxml.speciation;

import dr.evomodel.speciation.BranchingLikelihood;
import dr.evomodel.speciation.BranchingModel;
import beast.evolution.tree.Tree;
import dr.xml.AbstractXMLObjectParser;
import dr.xml.ElementRule;
import dr.xml.XMLObject;
import dr.xml.XMLSyntaxRule;

/**
 */
public class BranchingLikelihoodParser extends AbstractXMLObjectParser {

    public static final String BRANCHING_LIKELIHOOD = "branchingLikelihood";
    public static final String MODEL = "model";
    public static final String TREE = "branchingTree";

    @Override
	public String getParserName() {
        return BRANCHING_LIKELIHOOD;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        XMLObject cxo = xo.getChild(MODEL);
        BranchingModel branchingModel = (BranchingModel) cxo.getChild(BranchingModel.class);

        cxo = xo.getChild(TREE);
        Tree treeModel = (Tree) cxo.getChild(Tree.class);

        return new BranchingLikelihood(treeModel, branchingModel);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element represents the likelihood of the tree given the demographic function.";
    }

    @Override
	public Class getReturnType() {
        return BranchingLikelihood.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(MODEL, new XMLSyntaxRule[]{
                    new ElementRule(BranchingModel.class)
            }),
            new ElementRule(TREE, new XMLSyntaxRule[]{
                    new ElementRule(Tree.class)
            }),
    };

}
