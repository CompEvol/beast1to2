/*
 * LeafTraitExtractorParser.java
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

package dr.evomodelxml.continuous;

import beast.evolution.tree.Tree;
import dr.evomodelxml.tree.TreeModelParser;
import dr.inference.model.CompoundParameter;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * @author Marc A. Suchard
 */
public class LeafTraitExtractorParser extends AbstractXMLObjectParser {

    public static final String NAME = "leafTraitParameter";
    public static final String SET_BOUNDS = "setBounds";

    @Override
    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Tree model = (Tree) xo.getChild(Tree.class);
        final CompoundParameter allTraits = (CompoundParameter) xo.getChild(CompoundParameter.class);

        String taxonString = (String) xo.getAttribute(TreeModelParser.TAXON);
        final int leafIndex = model.getTaxonIndex(taxonString);
        if (leafIndex == -1) {
            throw new XMLParseException("Unable to find taxon '" + taxonString + "' in trees.");
        }
        final RealParameter leafTrait = allTraits.getParameter(leafIndex);

        boolean setBounds = xo.getAttribute(SET_BOUNDS, true);
        if (setBounds) {

            RealParameter.DefaultBounds bound = new RealParameter.DefaultBounds(Double.MAX_VALUE, -Double.MAX_VALUE,
                    leafTrait.getDimension());
            leafTrait.addBounds(bound);
        }

        return leafTrait;
    */
		}

    @Override
    public XMLSyntaxRule[] getSyntaxRules() {
        return new XMLSyntaxRule[]{
                AttributeRule.newStringRule(TreeModelParser.TAXON),
                AttributeRule.newBooleanRule(SET_BOUNDS, true),
                new ElementRule(Tree.class),
                new ElementRule(CompoundParameter.class),
        };
    }

    @Override
    public String getParserDescription() {
        return "Parses the leaf trait parameter out of the compound parameter of an integrated trait likelihood";
    }

    @Override
    public Class getReturnType() {
        return RealParameter.class;
    }

    @Override
	public String getParserName() {
        return NAME;
    }
}
