/*
 * RateStatisticParser.java
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

import beast.evolution.branchratemodel.BranchRateModel;
import beast.evolution.branchratemodel.RateStatistic;
import beast.evolution.tree.Tree;
import dr.inference.model.Statistic;
import dr.xml.*;

/**
 */
public class RateStatisticParser extends AbstractXMLObjectParser {

    public static final String RATE_STATISTIC = "rateStatistic";
    public static final String MODE = "mode";
    public static final String MEAN = "mean";
    public static final String VARIANCE = "variance";
    public static final String COEFFICIENT_OF_VARIATION = "coefficientOfVariation";

    @Override
	public String getParserName() {
        return RATE_STATISTIC;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        final String name = xo.getAttribute(Statistic.NAME, xo.getId());
        final Tree tree = (Tree) xo.getChild(Tree.class);
        final BranchRateModel branchRateModel = (BranchRateModel) xo.getChild(BranchRateModel.class);

        final boolean internal = xo.getBooleanAttribute("internal");
        final boolean external = xo.getBooleanAttribute("external");

        if (!(internal || external)) {
            throw new XMLParseException("At least one of internal and external must be true!");
        }

        final String mode = xo.getStringAttribute(MODE);
        RateStatistic stat = new RateStatistic();
        stat.initByName("tree", tree, "branchratemodel", branchRateModel);
        return stat;
        }

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A statistic that returns the average of the branch rates";
    }

    @Override
	public Class getReturnType() {
        return RateStatistic.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(Tree.class),
            new ElementRule(BranchRateModel.class),
            AttributeRule.newBooleanRule("internal"),
            AttributeRule.newBooleanRule("external"),
            new StringAttributeRule("mode", "This attribute determines how the rates are summarized, can be one of (mean, variance, coefficientOfVariance)", new String[]{MEAN, VARIANCE, COEFFICIENT_OF_VARIATION}, false),
            new StringAttributeRule("name", "A name for this statistic primarily for the purposes of logging", true),
    };

}
