/*
 * SpeciesBindingsParser.java
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

import dr.evomodel.speciation.SpeciesBindings;

import java.util.ArrayList;
import java.util.List;

import beast.core.parameter.RealParameter;
import beast.evolution.alignment.TaxonSet;
import beast.evolution.speciation.GeneTreeForSpeciesTreeDistribution;
import beast.evolution.tree.Tree;
import dr.xml.*;

/**
 */
public class SpeciesBindingsParser extends AbstractXMLObjectParser {
    public static final String SPECIES = "species";
    public static final String GENE_TREES = "geneTrees";
    public static final String GTREE = "gtree";
    public static final String PLOIDY = "ploidy";


    @Override
	public String getParserName() {
        return SPECIES;
    }

    public class Binding {
    	List<GeneTreeForSpeciesTreeDistribution> genes;
    	public TaxonSet taxonset;
    	Tree sptree;
    	String popFunction;
    	RealParameter sppSplitPopulations, sppSplitPopulationsTop;

    	Binding(List<GeneTreeForSpeciesTreeDistribution> genes, TaxonSet taxonset) {
    		this.genes = genes;
    		this.taxonset = taxonset;
    	}
    }
    
    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        TaxonSet sp = new TaxonSet();
        for (int k = 0; k < xo.getChildCount(); ++k) {
            final Object child = xo.getChild(k);
            if (child instanceof TaxonSet) {
                sp.setInputValue("taxon", (TaxonSet) child);
            }
        }

        final XMLObject xogt = xo.getChild(GENE_TREES);
        final int nTrees = xogt.getChildCount();
        final Tree[] trees = new Tree[nTrees];
        double[] popFactors = new double[nTrees];

        List<GeneTreeForSpeciesTreeDistribution> genes = new ArrayList<>();
        for (int nt = 0; nt < trees.length; ++nt) {
            Object child = xogt.getChild(nt);
            if (!(child instanceof Tree)) {
                assert child instanceof XMLObject;
                popFactors[nt] = ((XMLObject) child).getDoubleAttribute(PLOIDY);
                child = ((XMLObject) child).getChild(Tree.class);

            } else {
                popFactors[nt] = -1;
            }
            trees[nt] = (Tree) child;
            GeneTreeForSpeciesTreeDistribution distr = new GeneTreeForSpeciesTreeDistribution();
            distr.setInputValue("tree", trees[nt]);
            distr.setInputValue("ploidy", popFactors[nt]);
            genes.add(distr);
        }

        try {
        	return new Binding(genes, sp);
//            return new SpeciesBindings(sp.toArray(new SpeciesBindings.SPinfo[sp.size()]), trees, popFactors);
        } catch (Error e) {
            throw new XMLParseException(e.getMessage());
        }
		}

    /* Can't be tree because XML parser supports usage of global tags only as main tags */
    ElementRule treeWithPloidy = new ElementRule(GTREE,
            new XMLSyntaxRule[]{AttributeRule.newDoubleRule(PLOIDY),
                    new ElementRule(Tree.class)}, 0, Integer.MAX_VALUE);
    //XMLSyntaxRule[] someTree = {new OrRule(new ElementRule(Tree.class), treeWithPloidy)};

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return new XMLSyntaxRule[]{
                new ElementRule(TaxonSet.class, 2, Integer.MAX_VALUE),
                // new ElementRule(GENE_TREES, someTree,  1, Integer.MAX_VALUE )
                new ElementRule(GENE_TREES,
                        new XMLSyntaxRule[]{
                                // start at 0 for only ploidy tree cases
                                new ElementRule(Tree.class, 0, Integer.MAX_VALUE),
                                treeWithPloidy
                        }),
        };
    }

    @Override
	public String getParserDescription() {
        return "Binds taxa in gene trees with species information.";
    }

    @Override
	public Class getReturnType() {
        return Binding.class;
    }

}
