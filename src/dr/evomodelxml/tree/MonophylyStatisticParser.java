/*
 * MonophylyStatisticParser.java
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

package dr.evomodelxml.tree;

import beast.evolution.alignment.Taxon;
import beast.evolution.alignment.TaxonSet;
import beast.evolution.tree.Tree;
import beast.math.distributions.MRCAPrior;
import beast1to2.Beast1to2Converter;
import dr.xml.*;

/**
 */
public class MonophylyStatisticParser extends AbstractXMLObjectParser {

    public static final String MONOPHYLY_STATISTIC = "monophylyStatistic";
    public static final String MRCA = "mrca";
    public static final String IGNORE = "ignore";

    @Override
	public String getParserName() {
        return MONOPHYLY_STATISTIC;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        String name = xo.getAttribute("name", xo.getId());

        Tree tree = (Tree) xo.getChild(Tree.class);

        XMLObject cxo = xo.getChild(MRCA);
        TaxonSet taxa = (TaxonSet) cxo.getChild(TaxonSet.class);
        if (taxa == null) {
        	TaxonSet taxa1 = new TaxonSet();
            for (int i = 0; i < cxo.getChildCount(); i++) {
                Object ccxo = cxo.getChild(i);
                if (ccxo instanceof Taxon) {
                    taxa1.setInputValue("taxon", ccxo);
                }
            }
            taxa = taxa1;
        }

        TaxonSet ignore = null;
        if (xo.hasChildNamed(IGNORE)) {
            cxo = xo.getChild(IGNORE);
            ignore = (TaxonSet) cxo.getChild(TaxonSet.class);
            if (ignore == null) {
            	TaxonSet taxa1 = new TaxonSet();
                for (int i = 0; i < cxo.getChildCount(); i++) {
                    Object ccxo = cxo.getChild(i);
                    if (ccxo instanceof Taxon) {
                        taxa1.setInputValue("taxon", ccxo);
                    }
                }
                ignore = taxa1;
            }
            throw new XMLParseException(Beast1to2Converter.NIY + " " + IGNORE + " attribute in " + getParserName());
        }

        MRCAPrior prior = new MRCAPrior();
        prior.initByName("monophyletic", true, "taxonset", taxa, "tree", tree);
        return prior;
        
//        try {
//            return new MonophylyStatistic(name, tree, taxa, ignore);
//        } catch (Tree.MissingTaxonException mte) {
//            throw new XMLParseException("Taxon, " + mte + ", in " + getParserName() + "was not found in the tree.");
//        }

    }

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A statistic that returns true if a given set of taxa are monophyletic for a given tree";
    }

    @Override
	public Class getReturnType() {
        return MRCAPrior.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new StringAttributeRule("id", "A name for this statistic for the purpose of logging", true),
            // Any tree will do, no need to insist on a Tree Model
            new ElementRule(Tree.class),
//            new ElementRule(MRCA, new XMLSyntaxRule[]{
//                    new XORRule(
//                            new ElementRule(Taxon.class, 1, Integer.MAX_VALUE),
//                            new ElementRule(TaxonSet.class)
//                    )
//            }),
            new ElementRule(IGNORE, new XMLSyntaxRule[]{
                    new XORRule(
                            new ElementRule(Taxon.class, 1, Integer.MAX_VALUE),
                            new ElementRule(TaxonSet.class)
                    )
            }, "An optional list of taxa to ignore from the test of monophyly", true)
    };

}
