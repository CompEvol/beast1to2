/*
 * ExternalLengthStatisticParser.java
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

import dr.evolution.util.Taxa;
import dr.evomodel.tree.ExternalLengthStatistic;
import beast.evolution.tree.Tree;
import dr.xml.*;

/**
 */
public class ExternalLengthStatisticParser extends AbstractXMLObjectParser {

    public static final String EXTERNAL_LENGTH_STATISTIC = "externalLengthStatistic";

    @Override
	public String getParserName() {
        return EXTERNAL_LENGTH_STATISTIC;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        String name = xo.getAttribute(Statistic.NAME, xo.getId());

        Tree tree = (Tree) xo.getChild(Tree.class);
        TaxonSet taxa = (TaxonSet) xo.getChild(Taxa.class);

        try {
            return new ExternalLengthStatistic(name, tree, taxa);
        } catch (Tree.MissingTaxonException mte) {
            throw new XMLParseException("Taxon, " + mte + ", in " + getParserName() + "was not found in the tree.");
        }
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A statistic that has as its value(s) the length of the external branch length(s) of a set of one or more taxa in a given tree";
    }

    @Override
	public Class getReturnType() {
        return ExternalLengthStatistic.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new ElementRule(Tree.class),
            new StringAttributeRule("name", "A name for this statistic primarily for the purposes of logging", true),
            new ElementRule(Taxa.class)
    };

}
