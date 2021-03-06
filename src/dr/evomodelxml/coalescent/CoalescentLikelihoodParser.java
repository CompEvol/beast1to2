/*
 * CoalescentLikelihoodParser.java
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

package dr.evomodelxml.coalescent;


import beast.core.BEASTInterface;
import beast.evolution.alignment.TaxonSet;
import beast.evolution.tree.TreeInterface;
import beast.evolution.tree.coalescent.Coalescent;
import beast.evolution.tree.coalescent.PopulationFunction;
import beast.evolution.tree.coalescent.TreeIntervals;
import dr.xml.*;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class CoalescentLikelihoodParser extends AbstractXMLObjectParser {

    public static final String COALESCENT_LIKELIHOOD = "coalescentLikelihood";
    public static final String MODEL = "model";
    public static final String POPULATION_TREE = "populationTree";
    public static final String POPULATION_FACTOR = "factor";

    public static final String INCLUDE = "include";
    public static final String EXCLUDE = "exclude";

    @Override
	public String getParserName() {
        return COALESCENT_LIKELIHOOD;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        XMLObject cxo = xo.getChild(MODEL);
        PopulationFunction populationFunction = (PopulationFunction) cxo.getChild(PopulationFunction.class);

        List<TreeInterface> trees = new ArrayList<TreeInterface>();
        for (int k = 0; k < xo.getChildCount(); ++k) {
            final Object child = xo.getChild(k);
            if (child instanceof XMLObject) {
                cxo = (XMLObject) child;
                if (cxo.getName().equals(POPULATION_TREE)) {
                    final TreeInterface t = (TreeInterface) cxo.getChild(TreeInterface.class);
                    assert t != null;
                    trees.add(t);

//                    popFactors.add(cxo.getAttribute(POPULATION_FACTOR, 1.0));
                }
            }
        }

        TreeInterface treeModel = null;
        TreeIntervals treeIntervals = null;
        if (trees.size() == 1) { //&& popFactors.get(0) == 1.0) {
            treeModel = trees.get(0);
        } else if (trees.size() > 1) {
            throw new UnsupportedOperationException(getParserName() + " multiple loci " + beast1to2.Beast1to2Converter.NIY);
//            treesSet = new MultiLociTreeSet.Default(trees, popFactors);
        } else {//if (!(trees.size() == 0 && treesSet != null)) {
        	
        	treeIntervals = anyPredecssingIntervals((BEASTInterface) populationFunction);
        	if (treeIntervals == null) {
        		throw new XMLParseException("Incorrectly constructed likelihood element");
        	}           
        }

        if (treeModel != null || treeIntervals != null) {
            Coalescent coal = new Coalescent();
            if (treeIntervals == null) {
            	treeIntervals = new TreeIntervals();
            	treeIntervals.initByName("tree", treeModel);
            }
            coal.initByName("treeIntervals", treeIntervals, "populationModel", populationFunction);

            return coal;
        } else {
            throw new UnsupportedOperationException(getParserName() + " multiple loci " + beast1to2.Beast1to2Converter.NIY);
        }


		/*

        XMLObject cxo = xo.getChild(MODEL);
        DemographicModel demoModel = (DemographicModel) cxo.getChild(DemographicModel.class);

        List<Tree> trees = new ArrayList<Tree>();
        List<Double> popFactors = new ArrayList<Double>();
        MultiLociTreeSet treesSet = demoModel instanceof MultiLociTreeSet ? (MultiLociTreeSet) demoModel : null;

        for (int k = 0; k < xo.getChildCount(); ++k) {
            final Object child = xo.getChild(k);
            if (child instanceof XMLObject) {
                cxo = (XMLObject) child;
                if (cxo.getName().equals(POPULATION_TREE)) {
                    final Tree t = (Tree) cxo.getChild(Tree.class);
                    assert t != null;
                    trees.add(t);

                    popFactors.add(cxo.getAttribute(POPULATION_FACTOR, 1.0));
                }
            }
//                in the future we may have arbitrary multi-loci element
//                else if( child instanceof MultiLociTreeSet )  {
//                    treesSet = (MultiLociTreeSet)child;
//                }
        }

        Tree treeModel = null;
        if (trees.size() == 1 && popFactors.get(0) == 1.0) {
            treeModel = trees.get(0);
        } else if (trees.size() > 1) {
            treesSet = new MultiLociTreeSet.Default(trees, popFactors);
        } else if (!(trees.size() == 0 && treesSet != null)) {
            throw new XMLParseException("Incorrectly constructed likelihood element");
        }

        TaxonSet includeSubtree = null;

        if (xo.hasChildNamed(INCLUDE)) {
            includeSubtree = (TaxonSet) xo.getElementFirstChild(INCLUDE);
        }

        List<TaxonSet> excludeSubtrees = new ArrayList<TaxonSet>();

        if (xo.hasChildNamed(EXCLUDE)) {
            cxo = xo.getChild(EXCLUDE);
            for (int i = 0; i < cxo.getChildCount(); i++) {
                excludeSubtrees.add((TaxonSet) cxo.getChild(i));
            }
        }

        if (treeModel != null) {
            try {
                return new CoalescentLikelihood(treeModel, includeSubtree, excludeSubtrees, demoModel);
            } catch (Tree.MissingTaxonException mte) {
                throw new XMLParseException("treeModel missing a taxon from taxon list in " + getParserName() + " element");
            }
        } else {
            if (includeSubtree != null || excludeSubtrees.size() > 0) {
                throw new XMLParseException("Include/Exclude taxa not supported for multi locus sets");
            }
            // Use old code for multi locus sets.
            // This is a little unfortunate but the current code is using AbstractCoalescentLikelihood as
            // a base - and modifing it will probsbly result in a bigger mess.
            return new OldAbstractCoalescentLikelihood(treesSet, demoModel);
        }
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    private TreeIntervals anyPredecssingIntervals(BEASTInterface o) {
    	if (o instanceof TreeIntervals) {
    		return (TreeIntervals) o;
    	}
    	for (BEASTInterface i : o.listActiveBEASTObjects()) {
    		TreeIntervals o2 = anyPredecssingIntervals(i);
    		if (o2 != null) {
    			return o2;
    		}
    	}
		return null;
	}

	@Override
	public String getParserDescription() {
        return "This element represents the likelihood of the tree given the demographic function.";
    }

    @Override
	public Class getReturnType() {
        return Coalescent.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(MODEL, new XMLSyntaxRule[]{
                    new ElementRule(PopulationFunction.class)
            }, "The demographic model which describes the effective population size over time"),

            new ElementRule(POPULATION_TREE, new XMLSyntaxRule[]{
                    AttributeRule.newDoubleRule(POPULATION_FACTOR, true),
                    new ElementRule(TreeInterface.class)
            }, "Tree(s) to compute likelihood for", 0, Integer.MAX_VALUE),

            new ElementRule(INCLUDE, new XMLSyntaxRule[]{
                    new ElementRule(TaxonSet.class)
            }, "An optional subset of taxa on which to calculate the likelihood (should be monophyletic)", true),

            new ElementRule(EXCLUDE, new XMLSyntaxRule[]{
                    new ElementRule(TaxonSet.class, 1, Integer.MAX_VALUE)
            }, "One or more subsets of taxa which should be excluded from calculate the likelihood (should be monophyletic)", true)
    };
}
