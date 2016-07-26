/*
 * DiscretizedBranchRatesParser.java
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


import dr.xml.*;

import java.util.logging.Logger;

import beast.core.parameter.IntegerParameter;
import beast.core.parameter.Parameter;
import beast.core.util.Log;
import beast.evolution.branchratemodel.UCRelaxedClockModel;
import beast.evolution.tree.Tree;
import beast.math.distributions.ParametricDistribution;
import beast1to2.Beast1to2Converter;

/**
 * @author Alexei Drummond
 */
public class DiscretizedBranchRatesParser extends AbstractXMLObjectParser {

    public static final String DISCRETIZED_BRANCH_RATES = "discretizedBranchRates";
    public static final String DISTRIBUTION = "distribution";
    public static final String RATE_CATEGORIES = "rateCategories";
    public static final String SINGLE_ROOT_RATE = "singleRootRate";
    public static final String OVERSAMPLING = "overSampling";
    public static final String NORMALIZE = "normalize";
    public static final String NORMALIZE_BRANCH_RATE_TO = "normalizeBranchRateTo";
    public static final String RANDOMIZE_RATES = "randomizeRates";
    public static final String KEEP_RATES = "keepRates";
    public static final String CACHED_RATES = "cachedRates";

    //public static final String NORMALIZED_MEAN = "normalizedMean";


    @Override
	public String getParserName() {
        return DISCRETIZED_BRANCH_RATES;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        final int overSampling = xo.getAttribute(OVERSAMPLING, 1);
        if (overSampling != 1) {
        	Log.warning.println(Beast1to2Converter.NIY + " oversampling attribute is ignored");
        }

        //final boolean normalize = xo.getBooleanAttribute(NORMALIZE, false);
        final boolean normalize = xo.getAttribute(NORMALIZE, false);
//        if(xo.hasAttribute(NORMALIZE))
//            normalize = xo.getBooleanAttribute(NORMALIZE);
//		}
        //final double normalizeBranchRateTo = xo.getDoubleAttribute(NORMALIZE_BRANCH_RATE_TO);
        final double normalizeBranchRateTo = xo.getAttribute(NORMALIZE_BRANCH_RATE_TO, Double.NaN);
        if (!Double.isNaN(normalizeBranchRateTo)) {
        	Log.warning.println(Beast1to2Converter.NIY + " " + NORMALIZE_BRANCH_RATE_TO + "  attribute is ignored");
        }

        Tree tree = (Tree) xo.getChild(Tree.class);
        ParametricDistribution distributionModel = (ParametricDistribution) xo.getElementFirstChild(DISTRIBUTION);

        
        Parameter rateCategoryParameter0 = (Parameter<?>) xo.getElementFirstChild(RATE_CATEGORIES);
        IntegerParameter rateCategoryParameter = null;
        for (int i = 0; i < xo.getChildCount(); i++) {
        	Object o = xo.getRawChild(i); 
        	if (o instanceof XMLObject) {
        		XMLObject xco = (XMLObject) o;
        		if (xco.getName().equals(RATE_CATEGORIES)) {
        			o = xco.getRawChild(0);
        			rateCategoryParameter = new IntegerParameter();
        			rateCategoryParameter.initByName("dimension", Math.max(2, rateCategoryParameter0.getDimension()),
        					"value", "0");
        			((XMLObject)o).setNativeObject(rateCategoryParameter);
        		}
        	}
        }

//        Logger.getLogger("dr.evomodel").info("Using discretized relaxed clock model.");
//        Logger.getLogger("dr.evomodel").info("  over sampling = " + overSampling);
//        Logger.getLogger("dr.evomodel").info("  parametric model = " + distributionModel.getModelName());
//        Logger.getLogger("dr.evomodel").info("   rate categories = " + rateCategoryParameter.getDimension());
        if(normalize) {
            Logger.getLogger("dr.evomodel").info("   mean rate is normalized to " + normalizeBranchRateTo);
        }

        if (xo.hasAttribute(SINGLE_ROOT_RATE)) {
            //singleRootRate = xo.getBooleanAttribute(SINGLE_ROOT_RATE);
            Logger.getLogger("dr.evomodel").warning("   WARNING: single root rate is not implemented!");
        }

        final boolean randomizeRates = xo.getAttribute(RANDOMIZE_RATES, true);
        if (!randomizeRates) {
        	Log.warning.println(Beast1to2Converter.NIY + " " + RANDOMIZE_RATES + "  attribute is ignored");
        }
        final boolean keepRates = xo.getAttribute(KEEP_RATES, false);
        if (keepRates) {
        	Log.warning.println(Beast1to2Converter.NIY + " " + KEEP_RATES + "  attribute is ignored");
        }

        final boolean cachedRates = xo.getAttribute(CACHED_RATES, false);
        if (cachedRates) {
        	Log.warning.println(Beast1to2Converter.NIY + " " + CACHED_RATES + "  attribute is ignored");
        }

        if (randomizeRates && keepRates) {
            throw new XMLParseException("Unable to both randomize and keep current rate categories");
        }

//         if (xo.hasAttribute(NORMALIZED_MEAN)) {
//            dbr.setNormalizedMean(xo.getDoubleAttribute(NORMALIZED_MEAN));
//        }

        UCRelaxedClockModel clockmodel = new UCRelaxedClockModel();
        clockmodel.initByName("tree", tree, "distr", distributionModel, "normalize", normalize, "rateCategories", rateCategoryParameter);
        return clockmodel;
    }

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return
                "This element returns an discretized relaxed clock model." +
                        "The branch rates are drawn from a discretized parametric distribution.";
    }

    @Override
	public Class getReturnType() {
        return UCRelaxedClockModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newBooleanRule(SINGLE_ROOT_RATE, true, "Whether only a single rate should be used for the two children branches of the root"),
            //AttributeRule.newDoubleRule(NORMALIZED_MEAN, true, "The mean rate to constrain branch rates to once branch lengths are taken into account"),
            AttributeRule.newIntegerRule(OVERSAMPLING, true, "The integer factor for oversampling the distribution model (1 means no oversampling)"),
            AttributeRule.newBooleanRule(NORMALIZE, true, "Whether the mean rate has to be normalized to a particular value"),
            AttributeRule.newDoubleRule(NORMALIZE_BRANCH_RATE_TO, true, "The mean rate to normalize to, if normalizing"),
            AttributeRule.newBooleanRule(RANDOMIZE_RATES, true, "Randomize initial categories"),
            AttributeRule.newBooleanRule(KEEP_RATES, true, "Keep current rate category specification"),
            AttributeRule.newBooleanRule(CACHED_RATES, true, "Cache rates between steps (default off)"),
            new ElementRule(Tree.class),
            new ElementRule(DISTRIBUTION, ParametricDistribution.class, "The distribution model for rates among branches", false),
            new ElementRule(RATE_CATEGORIES, Parameter.class, "The rate categories parameter", false),
    };
}
