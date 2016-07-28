/*
 * MultiSpeciesCoalescentParser.java
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

import beast.core.util.CompoundDistribution;
import beast.evolution.speciation.GeneTreeForSpeciesTreeDistribution;
import beast.evolution.speciation.SpeciesTreePrior;
import beast.evolution.tree.Tree;
import dr.xml.*;

/**
 */
public class MultiSpeciesCoalescentParser extends AbstractXMLObjectParser {
    public static final String SPECIES_COALESCENT = "speciesCoalescent";

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        final SpeciesBindingsParser.Binding sb = (SpeciesBindingsParser.Binding) xo.getChild(SpeciesBindingsParser.Binding.class);
        final Tree sptree = (Tree) xo.getChild(Tree.class);
		CompoundDistribution distr = new CompoundDistribution();
		
		SpeciesTreePrior prior = new SpeciesTreePrior();
		prior.initByName("tree", sb.sptree, 
				"popFunction", sb.popFunction,
				"bottomPopSize", sb.sppSplitPopulations,
				"topPopSize", sb.sppSplitPopulationsTop,
				"taxonset", sb.taxonset,
				"gammaParameter", "1.0"
		);
		distr.pDistributions.get().add(prior);
		for (GeneTreeForSpeciesTreeDistribution d : sb.genes) {
			d.initByName("speciesTree", sptree, "speciesTreePrior", prior);
			distr.pDistributions.get().add(d);
		}
		return distr;
        //return new MultiSpeciesCoalescent(sb, tree);
	}

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return new XMLSyntaxRule[]{
                new ElementRule(SpeciesBindingsParser.Binding.class),
                new ElementRule(Tree.class),
        };
    }

    @Override
	public String getParserDescription() {
        return "Compute coalecent log-liklihood of a set of gene trees embedded inside one species tree.";
    }

    @Override
	public Class getReturnType() {
        return CompoundDistribution.class;
    }

    @Override
	public String getParserName() {
        return SPECIES_COALESCENT;
    }
}
