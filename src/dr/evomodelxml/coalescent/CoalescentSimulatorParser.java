/*
 * CoalescentSimulatorParser.java
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

import beast.core.util.Log;
import beast.evolution.alignment.TaxonSet;
import beast.evolution.tree.RandomTree;
import beast.evolution.tree.TraitSet;
import beast.evolution.tree.Tree;
import beast.evolution.tree.coalescent.ConstantPopulation;
import beast.evolution.tree.coalescent.PopulationFunction;
import beast1to2.Beast1to2Converter;
import dr.evoxml.TaxonParser;
import dr.xml.*;

import java.util.ArrayList;
import java.util.List;

/**
 * CoalescentSimulator parser. Replaces the one now called 'OldCoalescentSimulator' which
 * used the parser name 'coalescentTree'. Simulates a tree using arbitrarily nested subtrees
 * which can be scaled to have particular node heights to be compatible with calibrations.
 */
public class CoalescentSimulatorParser extends AbstractXMLObjectParser {

    public static final String COALESCENT_SIMULATOR = "coalescentSimulator";
    public static final String HEIGHT = "height";

    @Override
	public String getParserName() {
        return COALESCENT_SIMULATOR;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        RandomTree randomTree = new RandomTree();

        PopulationFunction populationFunction = (PopulationFunction) xo.getChild(PopulationFunction.class);
        List<TaxonSet> taxonLists = new ArrayList<TaxonSet>();
        List<Tree> subtrees = new ArrayList<Tree>();

        double height = xo.getAttribute(HEIGHT, Double.NaN);
        if (!Double.isNaN(height)) {
        	Log.warning.println(Beast1to2Converter.NIY + " height attribute is ignored");
        }
        // should have one child that is node
        for (int i = 0; i < xo.getChildCount(); i++) {
            final Object child = xo.getChild(i);

            // AER - swapped the order of these round because Trees are TaxonLists...
            if (child instanceof Tree) {
                throw new UnsupportedOperationException(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
//                subtrees.add((Tree) child);
            } else if (child instanceof TaxonSet) {
                taxonLists.add((TaxonSet) child);
                for (TraitSet t : TaxonParser.traits.values()) {
                    randomTree.m_traitList.get().add(t);
                }
            }
        }

        if (taxonLists.size() == 0) {
            if (subtrees.size() == 1) {
                return subtrees.get(0);
            }
            throw new XMLParseException("Expected at least one taxonList or two subtrees in "
                    + getParserName() + " element.");
        }


        if (taxonLists.size() > 1)
            throw new UnsupportedOperationException(getParserName() + " multi-taxonset " + beast1to2.Beast1to2Converter.NIY);

        try {
//            if (TaxonParser.traits.size() > 0) {
//                if (TaxonParser.traits.containsKey(TraitSet.DATE_TRAIT)) {
//                    randomTree.initByName("taxonset", taxonLists.get(0), "populationModel", populationFunction, 
//                    		"rootHeight", height, "trait", TaxonParser.traits.get(TraitSet.DATE_TRAIT));
//                } else if (TaxonParser.traits.containsKey(TraitSet.DATE_FORWARD_TRAIT)) {
//                    ClusterTree clusterTree = new ClusterTree();
//                    clusterTree.initByName("clusterType", "upgma", "trait", TaxonParser.traits.get(TraitSet.DATE_FORWARD_TRAIT));
//                    return clusterTree;
//                } else if (TaxonParser.traits.containsKey(TraitSet.DATE_BACKWARD_TRAIT)) {
//                    ClusterTree clusterTree = new ClusterTree();
//                    clusterTree.initByName("clusterType", "upgma", "trait", TaxonParser.traits.get(TraitSet.DATE_BACKWARD_TRAIT));
//                    return clusterTree;
//                } else if (TaxonParser.traits.containsKey("location")) {
//                    throw new UnsupportedOperationException(getParserName() + " traits location " + beast1to2.Beast1to2Converter.NIY);
//                } else {
//                    throw new UnsupportedOperationException(getParserName() + " traits " + beast1to2.Beast1to2Converter.NIY);
//                }
//
//            } else {
//                randomTree.initByName("taxonset", taxonLists.get(0), "populationModel", populationFunction, "rootHeight", height);
//
//                return randomTree;
//            }
            randomTree.initByName("taxonset", taxonLists.get(0), "populationModel", populationFunction);
        } catch (Exception iae) {
            throw new XMLParseException(iae.getMessage());
        }
        return randomTree;
        
		/*

        CoalescentSimulator simulator = new CoalescentSimulator();

        DemographicModel demoModel = (DemographicModel) xo.getChild(DemographicModel.class);
        List<TaxonSet> taxonLists = new ArrayList<TaxonSet>();
        List<Tree> subtrees = new ArrayList<Tree>();

        double height = xo.getAttribute(HEIGHT, Double.NaN);

        // should have one child that is node
        for (int i = 0; i < xo.getChildCount(); i++) {
            final Object child = xo.getChild(i);

            // AER - swapped the order of these round because Trees are TaxonLists...
            if (child instanceof Tree) {
                subtrees.add((Tree) child);
            } else if (child instanceof TaxonSet) {
                taxonLists.add((TaxonSet) child);
            }
          }

        if (taxonLists.size() == 0) {
            if (subtrees.size() == 1) {
                return subtrees.get(0);
            }
            throw new XMLParseException("Expected at least one taxonList or two subtrees in "
                    + getParserName() + " element.");
        }

        Taxa remainingTaxa = new Taxa();
        for (int i = 0; i < taxonLists.size(); i++) {
            remainingTaxa.addTaxa(taxonLists.get(i));
        }

        for (int i = 0; i < subtrees.size(); i++) {
            remainingTaxa.removeTaxa(subtrees.get(i));
        }

        try {
            Tree[] trees = new Tree[subtrees.size() + remainingTaxa.getTaxonCount()];
            // add the preset trees
            for (int i = 0; i < subtrees.size(); i++) {
                trees[i] = subtrees.get(i);
            }

            // add all the remaining taxa in as single tip trees...
            for (int i = 0; i < remainingTaxa.getTaxonCount(); i++) {
                Taxa tip = new Taxa();
                tip.addTaxon(remainingTaxa.getTaxon(i));
                trees[i + subtrees.size()] = simulator.simulateTree(tip, demoModel);
            }

            return simulator.simulateTree(trees, demoModel, height, trees.length != 1);

        } catch (IllegalArgumentException iae) {
            throw new XMLParseException(iae.getMessage());
        }
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element returns a simulated tree under the given demographic model. The element can " +
                "be nested to simulate with monophyletic clades. The tree will be rescaled to the given height.";
    }

    @Override
	public Class getReturnType() {
        return Tree.class; //Object.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newDoubleRule(HEIGHT, true, ""),
            new ElementRule(Tree.class, 0, Integer.MAX_VALUE),
            new ElementRule(TaxonSet.class, 0, Integer.MAX_VALUE),
            new ElementRule(ConstantPopulation.class, 0, Integer.MAX_VALUE),
    };
}
