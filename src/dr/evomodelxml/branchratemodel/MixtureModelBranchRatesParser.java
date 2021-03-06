/*
 * MixtureModelBranchRatesParser.java
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

import dr.evomodel.branchratemodel.MixtureModelBranchRates;
import beast.evolution.tree.Tree;
import dr.inference.distribution.ParametricDistributionModel;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * @author Wai Lok Sibon Li
 */
public class MixtureModelBranchRatesParser extends AbstractXMLObjectParser {

    public static final String MIXTURE_MODEL_BRANCH_RATES = "mixtureModelBranchRates";
    public static final String DISTRIBUTION = "distribution";
    public static final String RATE_CATEGORY_QUANTILES = "rateCategoryQuantiles";
    public static final String SINGLE_ROOT_RATE = "singleRootRate";
    public static final String NORMALIZE = "normalize";
    public static final String NORMALIZE_BRANCH_RATE_TO = "normalizeBranchRateTo";
    public static final String DISTRIBUTION_INDEX = "distributionIndex";

    public static final String USE_QUANTILE = "useQuantilesForRates";
    //public static final String NORMALIZED_MEAN = "normalizedMean";


    @Override
	public String getParserName() {
        return MIXTURE_MODEL_BRANCH_RATES;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        ArrayList<ParametricDistributionModel> modelsList = new ArrayList<ParametricDistributionModel>();

        final boolean normalize = xo.getAttribute(NORMALIZE, false);
        final double normalizeBranchRateTo = xo.getAttribute(NORMALIZE_BRANCH_RATE_TO, Double.NaN);

        final boolean useQuantilesForRates = xo.getAttribute(USE_QUANTILE, true);

        Tree tree = (Tree) xo.getChild(Tree.class);

        //while (xo.hasChildNamed(DISTRIBUTION)) {

        for (int i = 0; i < xo.getChildCount(); i++) {
            Object child = xo.getChild(i);
            if( child instanceof XMLObject ) {
                if( ((XMLObject) child).getName().equals(DISTRIBUTION) ) {
                    XMLObject childXML = (XMLObject) child;
                    modelsList.add((ParametricDistributionModel) childXML.getChild(0));
                }
            }
        }

        //RealParameter rateCategoryParameter = (RealParameter) xo.getElementFirstChild(RATE_CATEGORIES);

        ParametricDistributionModel[] models = modelsList.toArray(new ParametricDistributionModel[modelsList.size()]);

        RealParameter rateCategoryQuantilesParameter = (RealParameter) xo.getElementFirstChild(RATE_CATEGORY_QUANTILES);

        RealParameter distributionIndexParameter = (RealParameter) xo.getElementFirstChild(DISTRIBUTION_INDEX);

        Logger.getLogger("dr.evomodel").info("Using random discretized relaxed clock model with a mixture distribution.");
        for(int i=0; i<models.length; i++) {
            Logger.getLogger("dr.evomodel").info("  parametric model " + (i+1) +" = " + models[i].getModelName());
        }
        //Logger.getLogger("dr.evomodel").info("   rate categories = " + rateCategoryParameter.getDimension());
        Logger.getLogger("dr.evomodel").info("   rate categories = " + rateCategoryQuantilesParameter.getDimension());
        if(normalize) {
            Logger.getLogger("dr.evomodel").info("   mean rate is normalized to " + normalizeBranchRateTo);
        }

        if (xo.hasAttribute(SINGLE_ROOT_RATE)) {
            //singleRootRate = xo.getBooleanAttribute(SINGLE_ROOT_RATE);
            Logger.getLogger("dr.evomodel").warning("   WARNING: single root rate is not implemented!");
        }


        if(!useQuantilesForRates) {
            Logger.getLogger("dr.evomodel").info("Rates are set to not being drawn using quantiles. Thus they are not drawn from any particular distribution.");
        }
        /* if (xo.hasAttribute(NORMALIZED_MEAN)) {
            dbr.setNormalizedMean(xo.getDoubleAttribute(NORMALIZED_MEAN));
        }* /

        return new MixtureModelBranchRates(tree, rateCategoryQuantilesParameter, models, distributionIndexParameter, useQuantilesForRates, normalize, normalizeBranchRateTo);
*/    }

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
        return MixtureModelBranchRates.class;
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
            AttributeRule.newBooleanRule(USE_QUANTILE, true, "Whether or not to use quantiles to represent rates. If false then rates are not drawn " +
                "specifically from any of the distributions"),
            new ElementRule(Tree.class),
            //new ElementRule(DISTRIBUTION, ParametricDistributionModel.class, "The distribution model for rates among branches", false),
            /* Can have an infinite number of rate distribution models */
            new ElementRule(DISTRIBUTION, ParametricDistributionModel.class, "The distribution model for rates among branches", 1, Integer.MAX_VALUE),
            new ElementRule(DISTRIBUTION_INDEX, RealParameter.class, "Operator that switches between the distributions of the branch rate distribution model", false),
            /*new ElementRule(RATE_CATEGORIES, RealParameter.class, "The rate categories parameter", false),      */
            new ElementRule(RATE_CATEGORY_QUANTILES, RealParameter.class, "The quantiles for", false),
    };

		}