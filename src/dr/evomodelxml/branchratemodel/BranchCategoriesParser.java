/*
 * BranchCategoriesParser.java
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

package dr.evomodelxml.branchratemodel;

import dr.evolution.util.Taxa;
import dr.evomodel.branchratemodel.CountableBranchCategoryProvider;
import beast.evolution.tree.Tree;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 */
public class BranchCategoriesParser extends AbstractXMLObjectParser {

    public static final String BRANCH_CATEGORIES = "branchCategories";
    public static final String CATEGORY = "category";
    public static final String ALLOCATION = "rateCategories";

    public static final String RANDOMIZE = "randomize";

    @Override
	public String getParserName() {
        return BRANCH_CATEGORIES;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        RealParameter allocationParameter = (RealParameter) xo.getElementFirstChild(ALLOCATION);
        CountableBranchCategoryProvider cladeModel;
        Tree treeModel = (Tree) xo.getChild(Tree.class);

        if (!xo.getAttribute(RANDOMIZE, true)) {
            CountableBranchCategoryProvider.CladeBranchCategoryModel cm = new
                    CountableBranchCategoryProvider.CladeBranchCategoryModel(treeModel, allocationParameter);
            for (int i = 0; i < xo.getChildCount(); ++i) {
                if (xo.getChild(i) instanceof XMLObject) {
                    XMLObject xoc = (XMLObject) xo.getChild(i);
                    if (xoc.getName().equals(LocalClockModelParser.CLADE)) {
                        TaxonSet taxonList = (TaxonSet) xoc.getChild(TaxonSet.class);

                        boolean includeStem = xoc.getAttribute(LocalClockModelParser.INCLUDE_STEM, false);
                        boolean excludeClade = xoc.getAttribute(LocalClockModelParser.EXCLUDE_CLADE, false);
                        int rateCategory = xoc.getIntegerAttribute(CATEGORY) - 1; // XML index-start = 1 not 0
                        try {
                            cm.setClade(taxonList, rateCategory, includeStem, excludeClade, false);
                        } catch (Tree.MissingTaxonException e) {
                            throw new XMLParseException("Unable to find taxon for clade in countable mixture model: " + e.getMessage());
                        }
                    }  else if (xoc.getName().equals(LocalClockModelParser.TRUNK)) {
                        TaxonSet taxonList = (TaxonSet) xoc.getChild(TaxonSet.class);

                        boolean includeStem = xoc.getAttribute(LocalClockModelParser.INCLUDE_STEM, false);
                        boolean excludeClade = xoc.getAttribute(LocalClockModelParser.EXCLUDE_CLADE, false);
                        int rateCategory = xoc.getIntegerAttribute(CATEGORY) - 1; // XML index-start = 1 not 0
                        try {
                            cm.setClade(taxonList, rateCategory, includeStem, excludeClade, true);
                        } catch (Tree.MissingTaxonException e) {
                            throw new XMLParseException("Unable to find taxon for trunk in countable mixture model: " + e.getMessage());
                        }
                    }
                }
            }
            cladeModel = cm;
        } else {
            CountableBranchCategoryProvider.IndependentBranchCategoryModel cm = new CountableBranchCategoryProvider.IndependentBranchCategoryModel(treeModel, allocationParameter);
            cm.randomize();
            cladeModel = cm;
        }

        return cladeModel;
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return
                "This element provides a set of branch categories.";
    }

    @Override
	public Class getReturnType() {
        return CountableBranchCategoryProvider.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(Tree.class),
            new ElementRule(ALLOCATION, RealParameter.class, "Allocation parameter", false),
            AttributeRule.newBooleanRule(RANDOMIZE, true),
            new ElementRule(LocalClockModelParser.CLADE,
                    new XMLSyntaxRule[]{
//                            AttributeRule.newBooleanRule(RELATIVE, true),
                            AttributeRule.newIntegerRule(CATEGORY, false),
                            AttributeRule.newBooleanRule(LocalClockModelParser.INCLUDE_STEM, true, "determines whether or not the stem branch above this clade is included in the siteModel (default false)."),
                            AttributeRule.newBooleanRule(LocalClockModelParser.EXCLUDE_CLADE, true, "determines whether to exclude actual branches of the clade from the siteModel (default false)."),
                            new ElementRule(Taxa.class, "A set of taxa which defines a clade to apply a different site model to"),
                    }, 0, Integer.MAX_VALUE),
            new ElementRule(LocalClockModelParser.TRUNK,
                    new XMLSyntaxRule[]{
//                            AttributeRule.newBooleanRule(RELATIVE, true),
                            AttributeRule.newIntegerRule(CATEGORY, false),
                            AttributeRule.newBooleanRule(LocalClockModelParser.INCLUDE_STEM, true, "determines whether or not the stem branch above this clade is included in the siteModel (default false)."),
                            AttributeRule.newBooleanRule(LocalClockModelParser.EXCLUDE_CLADE, true, "determines whether to exclude actual branches of the clade from the siteModel (default false)."),
                            new ElementRule(Taxa.class, "A set of taxa which defines a clade to apply a different site model to"),
                    }, 0, Integer.MAX_VALUE),
    };
}
