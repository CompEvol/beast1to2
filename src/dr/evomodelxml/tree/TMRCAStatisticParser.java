/*
 * TMRCAStatisticParser.java
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

import beast.evolution.alignment.TaxonSet;
import beast.evolution.tree.Tree;
import beast.math.distributions.MRCAPrior;
import beast1to2.Beast1to2Converter;
import dr.xml.*;

/**
 * @author Alexei Drummond
 * @author Andrew Rambaut
 */
public class TMRCAStatisticParser extends AbstractXMLObjectParser {

    public static final String TMRCA_STATISTIC = "tmrcaStatistic";
    public static final String MRCA = "mrca";
    // The tmrcaStatistic will represent that age of the parent node of the MRCA, rather than the MRCA itself
    public static final String PARENT = "forParent";
    public static final String STEM = "includeStem";


    @Override
	public String getParserName() {
        return TMRCA_STATISTIC;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        String name = xo.getAttribute("name", xo.getId());
        Tree tree = (Tree) xo.getChild(Tree.class);
        TaxonSet taxa = (TaxonSet) xo.getElementFirstChild(MRCA);
        boolean isRate = xo.getAttribute("rate", false);
        if (isRate) {
            throw new XMLParseException(Beast1to2Converter.NIY + " rate=true");
        }
        boolean includeStem = false;
        if (xo.hasAttribute(PARENT) && xo.hasAttribute(STEM)) {
             throw new XMLParseException("Please use either " + PARENT + " or " + STEM + "!");
        } else if (xo.hasAttribute(PARENT)) {
             includeStem = xo.getBooleanAttribute(PARENT);
        } else if (xo.hasAttribute(STEM)) {
             includeStem = xo.getBooleanAttribute(STEM);
        }

//        try {
        	MRCAPrior prior = new MRCAPrior();
        	prior.initByName("tree", tree, "taxonset", taxa, "useOriginate", includeStem);
            return prior;
//        } catch (Exception mte) {
//            throw new XMLParseException(
//                    "Taxon, " + mte + ", in " + getParserName() + "was not found in the tree.");
//        }
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A statistic that has as its value the height of the most recent common ancestor " +
                "of a set of taxa in a given tree";
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
            new ElementRule(Tree.class),
            new StringAttributeRule("name",
                    "A name for this statistic primarily for the purposes of logging", true),
            AttributeRule.newBooleanRule("rate", true),
            new ElementRule(MRCA,
                    new XMLSyntaxRule[]{new ElementRule(TaxonSet.class)}),
            new OrRule(
                    new XMLSyntaxRule[]{
                            AttributeRule.newBooleanRule(PARENT, true),
                            AttributeRule.newBooleanRule(STEM, true)
                    }
            )
    };

}
