/*
 * SubtreeSlideOperatorParser.java
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


import beast.evolution.operators.SubtreeSlide;
import beast.evolution.tree.Tree;
import dr.inferencexml.operators.ScaleOperatorParser;
import dr.xml.*;

/**
 */
public class SubtreeSlideOperatorParser extends AbstractXMLObjectParser {

    public static final String SUBTREE_SLIDE = "subtreeSlide";
    public static final String SWAP_RATES = "swapInRandomRate";
    public static final String SWAP_TRAITS = "swapInRandomTrait";
    public static final String DIRICHLET_BRANCHES = "branchesAreScaledDirichlet";
    public static final String TARGET_ACCEPTANCE = "targetAcceptance";

    public static final String TRAIT = "trait";

    @Override
	public String getParserName() {
        return SUBTREE_SLIDE;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
//        boolean swapRates = xo.getAttribute(SWAP_RATES, false);
//        boolean swapTraits = xo.getAttribute(SWAP_TRAITS, false);
//        boolean scaledDirichletBranches = xo.getAttribute(DIRICHLET_BRANCHES, false);
//        final double targetAcceptance = xo.getAttribute(TARGET_ACCEPTANCE, 0.234);
        if (xo.hasAttribute(SWAP_RATES) || xo.hasAttribute(SWAP_TRAITS) ||
                xo.hasAttribute(DIRICHLET_BRANCHES) || xo.hasAttribute(TARGET_ACCEPTANCE)) {
            throw new UnsupportedOperationException(getParserName() + " " +
                    beast1to2.Beast1to2Converter.NIY);
        }

        Tree treeModel = (Tree) xo.getChild(Tree.class);
        final double weight = xo.getDoubleAttribute(ScaleOperatorParser.WEIGHT);
        final boolean optimise = xo.getAttribute(ScaleOperatorParser.AUTO_OPTIMIZE, true);

        final double size = xo.getAttribute("size", 1.0);

        if (Double.isInfinite(size) || size <= 0.0) {
            throw new XMLParseException("size attribute must be positive and not infinite. was " + size +
                    " for tree " + treeModel.getID() );
        }

        final boolean gaussian = xo.getBooleanAttribute("gaussian");

        SubtreeSlide subtreeSlide = new SubtreeSlide();
        subtreeSlide.initByName(ScaleOperatorParser.WEIGHT, weight, "tree", treeModel, "size", size,
                "optimise", optimise, "gaussian", gaussian);

        return subtreeSlide;

        /*boolean swapRates = xo.getAttribute(SWAP_RATES, false);
        boolean swapTraits = xo.getAttribute(SWAP_TRAITS, false);
        boolean scaledDirichletBranches = xo.getAttribute(DIRICHLET_BRANCHES, false);

        CoercionMode mode = CoercionMode.DEFAULT;
        if (xo.hasAttribute(CoercableMCMCOperator.AUTO_OPTIMIZE)) {
            if (xo.getBooleanAttribute(CoercableMCMCOperator.AUTO_OPTIMIZE)) {
                mode = CoercionMode.COERCION_ON;
            } else {
                mode = CoercionMode.COERCION_OFF;
            }
        }

        Tree treeModel = (Tree) xo.getChild(Tree.class);
        final double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);

        final double targetAcceptance = xo.getAttribute(TARGET_ACCEPTANCE, 0.234);

        final double size = xo.getAttribute("size", 1.0);

        if (Double.isInfinite(size) || size <= 0.0) {
            throw new XMLParseException("size attribute must be positive and not infinite. was " + size +
           " for tree " + treeModel.getId() );
        }

        final boolean gaussian = xo.getBooleanAttribute("gaussian");
        SubtreeSlideOperator operator = new SubtreeSlideOperator(treeModel, weight, size, gaussian,
                swapRates, swapTraits, scaledDirichletBranches, mode);
        operator.setTargetAcceptanceProbability(targetAcceptance);

        return operator;*/
    }

    @Override
	public String getParserDescription() {
        return "An operator that slides a subtree.";
    }

    @Override
	public Class getReturnType() {
        return SubtreeSlide.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newDoubleRule(ScaleOperatorParser.WEIGHT),
            // Make size optional. If not given or equals zero, size is set to half of average tree branch length.
            AttributeRule.newDoubleRule("size", true),
            AttributeRule.newDoubleRule(TARGET_ACCEPTANCE, true),
            AttributeRule.newBooleanRule("gaussian"),
            AttributeRule.newBooleanRule(SWAP_RATES, true),
            AttributeRule.newBooleanRule(SWAP_TRAITS, true),
            AttributeRule.newBooleanRule(ScaleOperatorParser.AUTO_OPTIMIZE, true),
            new ElementRule(Tree.class)
    };

}
