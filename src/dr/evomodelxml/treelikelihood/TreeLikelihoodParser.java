/*
 * TreeLikelihoodParser.java
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

package dr.evomodelxml.treelikelihood;

import beast.evolution.alignment.Alignment;
import beast.evolution.branchratemodel.BranchRateModel;
import beast.evolution.likelihood.TreeLikelihood;
import beast.evolution.sitemodel.SiteModelInterface;
import beast.evolution.tree.TreeInterface;
import dr.xml.*;

/**
 */
public class TreeLikelihoodParser extends AbstractXMLObjectParser {

    public static final String TREE_LIKELIHOOD = "treeLikelihood";
    public static final String ANCESTRAL_TREE_LIKELIHOOD = "ancestralTreeLikelihood";
    public static final String USE_AMBIGUITIES = "useAmbiguities";
    public static final String ALLOW_MISSING_TAXA = "allowMissingTaxa";
    public static final String STORE_PARTIALS = "storePartials";
    public static final String SCALING_FACTOR = "scalingFactor";
    public static final String SCALING_THRESHOLD = "scalingThreshold";
    public static final String FORCE_JAVA_CORE = "forceJavaCore";
    public static final String FORCE_RESCALING = "forceRescaling";


    @Override
	public String getParserName() {
        return TREE_LIKELIHOOD;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        boolean useAmbiguities = xo.getAttribute(USE_AMBIGUITIES, false);
//        boolean allowMissingTaxa = xo.getAttribute(ALLOW_MISSING_TAXA, false);
//        boolean storePartials = xo.getAttribute(STORE_PARTIALS, true);
//        boolean forceJavaCore = xo.getAttribute(FORCE_JAVA_CORE, false);
//
//        if (Boolean.valueOf(System.getProperty("java.only"))) {
//            forceJavaCore = true;
//        }

        Alignment patternList = (Alignment) xo.getChild(Alignment.class);
        TreeInterface treeModel = (TreeInterface) xo.getChild(TreeInterface.class);
        SiteModelInterface siteModel = (SiteModelInterface) xo.getChild(SiteModelInterface.class);

        BranchRateModel.Base branchRateModel = (BranchRateModel.Base) xo.getChild(BranchRateModel.Base.class);

//        TipStatesModel tipStatesModel = (TipStatesModel) xo.getChild(TipStatesModel.class);
//        if (tipStatesModel != null && tipStatesModel.getPatternList() != null) {
//            throw new XMLParseException("The same sequence error model cannot be used for multiple partitions");
//        }
//        if (tipStatesModel != null && tipStatesModel.getModelType() == TipStatesModel.Type.STATES) {
//            throw new XMLParseException("The state emitting TipStateModel requires BEAGLE");
//        }

//        boolean forceRescaling = xo.getAttribute(FORCE_RESCALING, false);

        TreeLikelihood treeLikelihood = new TreeLikelihood();
        treeLikelihood.initByName("data", patternList, "tree", treeModel, "siteModel", siteModel,
                "branchRateModel", branchRateModel, "useAmbiguities", useAmbiguities);

		return treeLikelihood;
		/*

        boolean useAmbiguities = xo.getAttribute(USE_AMBIGUITIES, false);
        boolean allowMissingTaxa = xo.getAttribute(ALLOW_MISSING_TAXA, false);
        boolean storePartials = xo.getAttribute(STORE_PARTIALS, true);
        boolean forceJavaCore = xo.getAttribute(FORCE_JAVA_CORE, false);

        if (Boolean.valueOf(System.getProperty("java.only"))) {
            forceJavaCore = true;
        }

        PatternList patternList = (PatternList) xo.getChild(PatternList.class);
        Tree treeModel = (Tree) xo.getChild(Tree.class);
        SiteModel siteModel = (SiteModel) xo.getChild(SiteModel.class);

        BranchRateModel branchRateModel = (BranchRateModel) xo.getChild(BranchRateModel.class);

        TipStatesModel tipStatesModel = (TipStatesModel) xo.getChild(TipStatesModel.class);
        if (tipStatesModel != null && tipStatesModel.getPatternList() != null) {
            throw new XMLParseException("The same sequence error model cannot be used for multiple partitions");
        }
        if (tipStatesModel != null && tipStatesModel.getModelType() == TipStatesModel.Type.STATES) {
            throw new XMLParseException("The state emitting TipStateModel requires BEAGLE");
        }


        boolean forceRescaling = xo.getAttribute(FORCE_RESCALING, false);

        return new TreeLikelihood(
                patternList,
                treeModel,
                siteModel,
                branchRateModel,
                tipStatesModel,
                useAmbiguities, allowMissingTaxa, storePartials, forceJavaCore, forceRescaling);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element represents the likelihood of a patternlist on a tree given the site model.";
    }

    @Override
	public Class getReturnType() {
        return TreeLikelihood.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newBooleanRule(USE_AMBIGUITIES, true),
//            AttributeRule.newBooleanRule(ALLOW_MISSING_TAXA, true),
//            AttributeRule.newBooleanRule(STORE_PARTIALS, true),
//            AttributeRule.newBooleanRule(FORCE_JAVA_CORE, true),
//            AttributeRule.newBooleanRule(FORCE_RESCALING, true),
            new ElementRule(Alignment.class),
            new ElementRule(TreeInterface.class),
            new ElementRule(SiteModelInterface.class),
            new ElementRule(BranchRateModel.Base.class, true)
//            new ElementRule(TipStatesModel.class, true)
    };
}
