/*
 * LatentStateBranchRateModelParser.java
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

import dr.evomodel.branchratemodel.*;
import beast.evolution.tree.Tree;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 */
public class LatentStateBranchRateModelParser extends AbstractXMLObjectParser {
    public static final String LATENT_TRANSITION_RATE = "latentTransitionRate";
    public static final String LATENT_TRANSITION_FREQUENCY = "latentTransitionFrequency";
    public static final String LATENT_STATE_PROPORTIONS = "latentStateProportions";


    @Override
	public String getParserName() {
        return SericolaLatentStateBranchRateModel.LATENT_STATE_BRANCH_RATE_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*
        BranchRateModel nonLatentRateModel = (BranchRateModel) xo.getChild(BranchRateModel.class);
        Tree tree = (Tree) xo.getChild(Tree.class);
        RealParameter latentTransitionRateParameter = (RealParameter) xo.getElementFirstChild(LATENT_TRANSITION_RATE);
        RealParameter latentTransitionFrequencyParameter = (RealParameter) xo.getElementFirstChild(LATENT_TRANSITION_FREQUENCY);

        CountableBranchCategoryProvider branchCategoryProvider = (CountableBranchCategoryProvider)xo.getChild(CountableBranchCategoryProvider.class);

        RealParameter latentStateProportionParameter = null;
        if (xo.hasChildNamed(LATENT_STATE_PROPORTIONS)) {
            latentStateProportionParameter = (RealParameter) xo.getElementFirstChild(LATENT_STATE_PROPORTIONS);
        }

        Logger.getLogger("dr.evomodel").info("Creating a latent state branch rate model");

        return new LatentStateBranchRateModel(LatentStateBranchRateModel.LATENT_STATE_BRANCH_RATE_MODEL,
                tree, nonLatentRateModel,
                latentTransitionRateParameter, latentTransitionFrequencyParameter, /* 0/1 CTMC have two parameters * /
                latentStateProportionParameter, branchCategoryProvider);
//        return new SericolaLatentStateBranchRateModel(SericolaLatentStateBranchRateModel.LATENT_STATE_BRANCH_RATE_MODEL,
//                tree, nonLatentRateModel,
//                latentTransitionRateParameter, latentTransitionFrequencyParameter, /* 0/1 CTMC have two parameters * /
//                latentStateProportionParameter, branchCategoryProvider);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element provides a model with a latent state where no evolution occurs but condition on being non-latent at the nodes.";
    }

    @Override
	public Class getReturnType()  {
        return SericolaLatentStateBranchRateModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{

            new ElementRule(BranchRateModel.class, "A branch rate model to provide the rates for the non-latent state"),
            new ElementRule(Tree.class, "The tree on which this will operate"),
            new ElementRule(CountableBranchCategoryProvider.class, true),
            new ElementRule(LATENT_TRANSITION_RATE, RealParameter.class, "A parameter which gives the instantaneous rate of switching to and from the latent state", false),
            new ElementRule(LATENT_TRANSITION_FREQUENCY, RealParameter.class, "A parameter which gives the rate bias of switching to and from the latent state", false),
            new ElementRule(LATENT_STATE_PROPORTIONS, RealParameter.class, "The proportion of each branch which is spend in a latent state", true)

    };

}
