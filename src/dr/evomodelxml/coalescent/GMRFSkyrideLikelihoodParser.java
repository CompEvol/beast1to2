/*
 * GMRFSkyrideLikelihoodParser.java
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

package dr.evomodelxml.coalescent;

import dr.xml.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import beast.core.parameter.RealParameter;
import beast.evolution.tree.Tree;
import beast.evolution.tree.coalescent.GMRFMultilocusSkyrideLikelihood;
import beast.evolution.tree.coalescent.GMRFSkyrideLikelihood;
import beast1to2.Beast1to2Converter;

/**
 *
 */
public class GMRFSkyrideLikelihoodParser extends AbstractXMLObjectParser {

    public static final String SKYLINE_LIKELIHOOD = "gmrfSkyrideLikelihood";
    public static final String SKYRIDE_LIKELIHOOD = "skyrideLikelihood";
    public static final String SKYGRID_LIKELIHOOD = "gmrfSkyGridLikelihood";

    public static final String POPULATION_PARAMETER = "populationSizes";
    public static final String GROUP_SIZES = "groupSizes";
    public static final String PRECISION_PARAMETER = "precisionParameter";
    public static final String POPULATION_TREE = "populationTree";
    public static final String LAMBDA_PARAMETER = "lambdaParameter";
    public static final String BETA_PARAMETER = "betaParameter";
    public static final String COVARIATE_MATRIX = "covariateMatrix";
    public static final String RANDOMIZE_TREE = "randomizeTree";
    public static final String TIME_AWARE_SMOOTHING = "timeAwareSmoothing";

    public static final String RESCALE_BY_ROOT_ISSUE = "rescaleByRootHeight";
    public static final String GRID_POINTS = "gridPoints";
    public static final String OLD_SKYRIDE = "oldSkyride";
    public static final String NUM_GRID_POINTS = "numGridPoints";
    public static final String CUT_OFF = "cutOff";
    public static final String PHI_PARAMETER = "phiParameter";
    public static final String PLOIDY = "ploidy";


    public String getParserName() {
        return SKYLINE_LIKELIHOOD;
    }

    public String[] getParserNames() {
        return new String[]{getParserName(), SKYRIDE_LIKELIHOOD, SKYGRID_LIKELIHOOD}; // cannot duplicate
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        XMLObject cxo = xo.getChild(POPULATION_PARAMETER);
        RealParameter popParameter = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(PRECISION_PARAMETER);
        RealParameter precParameter = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(POPULATION_TREE);

        List<Tree> treeList = new ArrayList<Tree>();
        for (int i = 0; i < cxo.getChildCount(); i++) {
            Object testObject = cxo.getChild(i);
            if (testObject instanceof Tree) {
                treeList.add((Tree) testObject);
            }
        }

//        TreeModel treeModel = (TreeModel) cxo.getChild(TreeModel.class);

        cxo = xo.getChild(GROUP_SIZES);
        RealParameter groupParameter = null;
        if (cxo != null) {
            groupParameter = (RealParameter) cxo.getChild(RealParameter.class);

            if (popParameter.getDimension() != groupParameter.getDimension())
                throw new XMLParseException("Population and group size parameters must have the same length");
        }

        RealParameter lambda;
        if (xo.getChild(LAMBDA_PARAMETER) != null) {
            cxo = xo.getChild(LAMBDA_PARAMETER);
            lambda = (RealParameter) cxo.getChild(RealParameter.class);
        } else {
            lambda = new RealParameter("1.0");
        }
        /*
        Parameter gridPoints = null;
        if (xo.getChild(GRID_POINTS) != null) {
            cxo = xo.getChild(GRID_POINTS);
            gridPoints = (Parameter) cxo.getChild(Parameter.class);
        }
        */
        RealParameter numGridPoints = null;
        if (xo.getChild(NUM_GRID_POINTS) != null) {
            cxo = xo.getChild(NUM_GRID_POINTS);
            numGridPoints = (RealParameter) cxo.getChild(RealParameter.class);
        }

        RealParameter cutOff = null;
        if (xo.getChild(CUT_OFF) != null) {
            cxo = xo.getChild(CUT_OFF);
            cutOff = (RealParameter) cxo.getChild(RealParameter.class);
        }

        RealParameter phi = null;
        if (xo.getChild(PHI_PARAMETER) != null) {
            cxo = xo.getChild(PHI_PARAMETER);
            phi = (RealParameter) cxo.getChild(RealParameter.class);
        }


        RealParameter ploidyFactors = null;
        if (xo.getChild(PLOIDY) != null) {
            cxo = xo.getChild(PLOIDY);
            ploidyFactors = (RealParameter) cxo.getChild(RealParameter.class);
        } else {
            Double [] values = new Double[treeList.size()];
            for (int i = 0; i < treeList.size(); i++) {
                values[i] = 1.0;
            }
            ploidyFactors = new RealParameter(values);
        }

        RealParameter beta = null;
        if (xo.getChild(BETA_PARAMETER) != null) {
            cxo = xo.getChild(BETA_PARAMETER);
            beta = (RealParameter) cxo.getChild(RealParameter.class);
            throw new RuntimeException(Beast1to2Converter.NIY + " " + SKYGRID_LIKELIHOOD + " attribute " + BETA_PARAMETER);
        }

        RealParameter dMatrix = null;
        if (xo.getChild(COVARIATE_MATRIX) != null) {
            cxo = xo.getChild(COVARIATE_MATRIX);
            dMatrix = (RealParameter) cxo.getChild(RealParameter.class);
        }

        boolean timeAwareSmoothing = GMRFSkyrideLikelihood.TIME_AWARE_IS_ON_BY_DEFAULT;
        if (xo.hasAttribute(TIME_AWARE_SMOOTHING)) {
            timeAwareSmoothing = xo.getBooleanAttribute(TIME_AWARE_SMOOTHING);
        }

        if ((dMatrix != null && beta == null) || (dMatrix == null && beta != null))
            throw new XMLParseException("Must specify both a set of regression coefficients and a design matrix.");

        if (dMatrix != null) {
            if (dMatrix.getMinorDimension1() != popParameter.getDimension())
                throw new XMLParseException("Design matrix row dimension must equal the population parameter length.");
            if (dMatrix.getMinorDimension2() != beta.getDimension())
                throw new XMLParseException("Design matrix column dimension must equal the regression coefficient length.");
        }

        if (xo.getAttribute(RANDOMIZE_TREE, false)) {
            for (Tree tree : treeList) {
                if (tree instanceof Tree) {
                	// RRB: skipping this check
                    // GMRFSkyrideLikelihood.checkTree((Tree) tree);
                } else {
                    throw new XMLParseException("Can not randomize a fixed tree");
                }
            }
        }

        boolean rescaleByRootHeight = xo.getAttribute(RESCALE_BY_ROOT_ISSUE, true);

        Logger.getLogger("dr.evomodel").info("The " + SKYLINE_LIKELIHOOD + " has " +
                (timeAwareSmoothing ? "time aware smoothing" : "uniform smoothing"));

        if (xo.getAttribute(OLD_SKYRIDE, true) && xo.getName().compareTo(SKYGRID_LIKELIHOOD) != 0) {

        	GMRFSkyrideLikelihood likelihood = new GMRFSkyrideLikelihood();
        	likelihood.initByName("tree", treeList.get(0), "populationSizes", popParameter, "groupSizes", groupParameter, "precisionParameter", precParameter,
                    "lambda", lambda, /* beta, dMatrix,*/ "timeAwareSmoothing", timeAwareSmoothing, "rescaleByRootHeightInput", rescaleByRootHeight);
            return likelihood;

        } else {
        	GMRFMultilocusSkyrideLikelihood likelihood = new GMRFMultilocusSkyrideLikelihood();
        	
        	likelihood.initByName("trees", treeList, "populationSizes", popParameter, "groupSizes", groupParameter, "precisionParameter", precParameter,
                    "lambda", lambda, /* beta, dMatrix,*/ "timeAwareSmoothing", timeAwareSmoothing, "rescaleByRootHeightInput", rescaleByRootHeight,
                    "cutOff", cutOff.getValue(), "beta", beta, "dMatrix", dMatrix, "numGridPoints", (int) (double) numGridPoints.getValue(),
                    "ploidyFactors", ploidyFactors, "phi", phi);

        	return likelihood;
        	
//            return new GMRFMultilocusSkyrideLikelihood(treeList, popParameter, groupParameter, precParameter,
//                    lambda, beta, dMatrix, timeAwareSmoothing, cutOff.getParameterValue(0), (int) numGridPoints.getParameterValue(0), phi, ploidyFactors);

        }
	}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public String getParserDescription() {
        return "This element represents the likelihood of the tree given the population size vector.";
    }

    public Class getReturnType() {
        return GMRFSkyrideLikelihood.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(POPULATION_PARAMETER, new XMLSyntaxRule[]{
                    new ElementRule(RealParameter.class)
            }),
            new ElementRule(PRECISION_PARAMETER, new XMLSyntaxRule[]{
                    new ElementRule(RealParameter.class)
            }),
            new ElementRule(PHI_PARAMETER, new XMLSyntaxRule[]{
                    new ElementRule(RealParameter.class)
            }, true), // Optional
            new ElementRule(POPULATION_TREE, new XMLSyntaxRule[]{
                    new ElementRule(Tree.class, 1, Integer.MAX_VALUE)
            }),
            new ElementRule(GROUP_SIZES, new XMLSyntaxRule[]{
                    new ElementRule(RealParameter.class)
            }, true),
            AttributeRule.newBooleanRule(RESCALE_BY_ROOT_ISSUE, true),
            AttributeRule.newBooleanRule(RANDOMIZE_TREE, true),
            AttributeRule.newBooleanRule(TIME_AWARE_SMOOTHING, true),
            AttributeRule.newBooleanRule(OLD_SKYRIDE, true)
    };

}
