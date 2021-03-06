/*
 * RandomDiscretizedBranchRatesParser.java
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

//import dr.evomodel.branchratemodel.RandomDiscretizedBranchRates;
import beast.evolution.tree.Tree;
import dr.inference.distribution.ParametricDistributionModel;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * @author Wai Lok Sibon Li
 */
public class RandomDiscretizedBranchRatesParser extends AbstractXMLObjectParser {

    public static final String RANDOM_DISCRETIZED_BRANCH_RATES = "randomDiscretizedBranchRates";
    public static final String DISTRIBUTION = "distribution";
    //public static final String RATE_CATEGORIES = "rateCategories";
    public static final String RATE_CATEGORY_QUANTILES = "rateCategoryQuantiles";
    public static final String SINGLE_ROOT_RATE = "singleRootRate";
    //public static final String OVERSAMPLING = "overSampling";
    public static final String NORMALIZE = "normalize";
    public static final String NORMALIZE_BRANCH_RATE_TO = "normalizeBranchRateTo";
    //public static final String NORMALIZED_MEAN = "normalizedMean";


    @Override
	public String getParserName() {
        return RANDOM_DISCRETIZED_BRANCH_RATES;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

//        throw new RuntimeException("randomDiscretizedBranchRates has been renamed to continuousBranchRates (all of the " +
//                "parameters have been kept the same). Please make changes to the XML before rerunning. ");
        System.err.println("randomDiscretizedBranchRates has been renamed to continuousBranchRates (all of the " +
                        "parameters have been kept the same). Please make changes to the XML before rerunning. ");
        System.exit(1);
        return null;

        //final int overSampling = xo.getAttribute(OVERSAMPLING, 1);
//        final boolean normalize = xo.getAttribute(NORMALIZE, false);
//        final double normalizeBranchRateTo = xo.getAttribute(NORMALIZE_BRANCH_RATE_TO, Double.NaN);
//
//        Tree tree = (Tree) xo.getChild(Tree.class);
//        ParametricDistributionModel distributionModel = (ParametricDistributionModel) xo.getElementFirstChild(DISTRIBUTION);
//
//        //RealParameter rateCategoryParameter = (RealParameter) xo.getElementFirstChild(RATE_CATEGORIES);
//
//        RealParameter rateCategoryQuantilesParameter = (RealParameter) xo.getElementFirstChild(RATE_CATEGORY_QUANTILES);
//
//        Logger.getLogger("dr.evomodel").info("Using random discretized relaxed clock model.");
//        //Logger.getLogger("dr.evomodel").info("  over sampling = " + overSampling);
//        Logger.getLogger("dr.evomodel").info("  parametric model = " + distributionModel.getModelName());
//        //Logger.getLogger("dr.evomodel").info("   rate categories = " + rateCategoryParameter.getDimension());
//        Logger.getLogger("dr.evomodel").info("   rate categories = " + rateCategoryQuantilesParameter.getDimension());
//        if(normalize) {
//            Logger.getLogger("dr.evomodel").info("   mean rate is normalized to " + normalizeBranchRateTo);
//        }
//
//        if (xo.hasAttribute(SINGLE_ROOT_RATE)) {
//            //singleRootRate = xo.getBooleanAttribute(SINGLE_ROOT_RATE);
//            Logger.getLogger("dr.evomodel").warning("   WARNING: single root rate is not implemented!");
//        }
//
//        /* if (xo.hasAttribute(NORMALIZED_MEAN)) {
//            dbr.setNormalizedMean(xo.getDoubleAttribute(NORMALIZED_MEAN));
//        }* /
//
//        return new RandomDiscretizedBranchRates(tree, /*rateCategoryParameter, * /rateCategoryQuantilesParameter, distributionModel, /*overSampling,* / normalize, normalizeBranchRateTo);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return
                "This element returns a random discretized relaxed clock model." +
                        "The branch rates are drawn from a continuous parametric distribution.";
    }

    @Override
	public Class getReturnType() {
        return null; //RandomDiscretizedBranchRates.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            AttributeRule.newBooleanRule(SINGLE_ROOT_RATE, true, "Whether only a single rate should be used for the two children branches of the root"),
            //AttributeRule.newDoubleRule(NORMALIZED_MEAN, true, "The mean rate to constrain branch rates to once branch lengths are taken into account"),
            //AttributeRule.newIntegerRule(OVERSAMPLING, true, "The integer factor for oversampling the distribution model (1 means no oversampling)"),
            AttributeRule.newBooleanRule(NORMALIZE, true, "Whether the mean rate has to be normalized to a particular value"),
            AttributeRule.newDoubleRule(NORMALIZE_BRANCH_RATE_TO, true, "The mean rate to normalize to, if normalizing"),
            new ElementRule(Tree.class),
            new ElementRule(DISTRIBUTION, ParametricDistributionModel.class, "The distribution model for rates among branches", false),
            /*new ElementRule(RATE_CATEGORIES, RealParameter.class, "The rate categories parameter", false),      */
            new ElementRule(RATE_CATEGORY_QUANTILES, RealParameter.class, "The quantiles for", false),
    };
}