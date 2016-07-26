/*
 * UCLikelihoodParser.java
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

package dr.evomodelxml.clock;

import dr.evomodel.clock.RateEvolutionLikelihood;
import dr.evomodel.clock.UCLikelihood;
import beast.evolution.tree.Tree;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 */
public class UCLikelihoodParser extends AbstractXMLObjectParser {

    public static final String UC_LIKELIHOOD = "UCLikelihood";

    public static final String VARIANCE = "variance";

    @Override
	public String getParserName() {
        return UC_LIKELIHOOD;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Tree tree = (Tree) xo.getChild(Tree.class);

        RealParameter ratesParameter = (RealParameter) xo.getElementFirstChild(RateEvolutionLikelihood.RATES);

        RealParameter rootRate = (RealParameter) xo.getElementFirstChild(RateEvolutionLikelihood.ROOTRATE);

        RealParameter variance = (RealParameter) xo.getElementFirstChild(VARIANCE);


        boolean isLogSpace = xo.getAttribute(RateEvolutionLikelihood.LOGSPACE, false);

        return new UCLikelihood(tree, ratesParameter, variance, rootRate, isLogSpace);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return
                "This element returns an object that can calculate the likelihood " +
                        "of rates in a tree under the assumption of " +
                        "(log)normally distributed rates. ";
    }

    @Override
	public Class getReturnType() {
        return UCLikelihood.class;
    }


    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(Tree.class),
            new ElementRule(RateEvolutionLikelihood.RATES, RealParameter.class, "The branch rates parameter", false),
            AttributeRule.newBooleanRule(RateEvolutionLikelihood.LOGSPACE, true, "true if model considers the log of the rates."),
            new ElementRule(RateEvolutionLikelihood.ROOTRATE, RealParameter.class, "The root rate parameter"),
            new ElementRule(VARIANCE, RealParameter.class, "The standard deviation of the (log)normal distribution"),
    };

}
