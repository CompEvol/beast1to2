/*
 * BinomialLikelihoodParser.java
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

package dr.inferencexml.distribution;

import dr.inference.distribution.BinomialLikelihood;
import dr.inference.model.Likelihood;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 *
 */
public class BinomialLikelihoodParser extends AbstractXMLObjectParser {

    public static final String TRIALS = "trials";
    public static final String COUNTS = "counts";
    public static final String PROPORTION = "proportion";
    public static final String VALUES = "values";
    public static final String ON_LOGIT_SCALE = "onLogitScale";

    @Override
	public String getParserName() {
        return BinomialLikelihood.BINOMIAL_LIKELIHOOD;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        final boolean onLogitScale = xo.getAttribute(ON_LOGIT_SCALE, false);

        XMLObject cxo = xo.getChild(COUNTS);
        RealParameter countsParam = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(PROPORTION);
        RealParameter proportionParam = (RealParameter) cxo.getChild(RealParameter.class);

        if (proportionParam.getDimension() != 1 && proportionParam.getDimension() != countsParam.getDimension()) {
            throw new XMLParseException("Proportion dimension (" + proportionParam.getDimension() + ") " +
            "must equal 1 or counts dimension (" + countsParam.getDimension() + ")");
        }

        cxo = xo.getChild(TRIALS);
        RealParameter trialsParam;
        if (cxo.hasAttribute(VALUES)) {
            int[] tmp = cxo.getIntegerArrayAttribute(VALUES);
            double[] v = new double[tmp.length];
            for (int i = 0; i < tmp.length; ++i) {
                v[i] = tmp[i];
            }
            trialsParam = new RealParameter.Default(v);
        } else {
            trialsParam = (RealParameter) cxo.getChild(RealParameter.class);
        }

        if (trialsParam.getDimension() != countsParam.getDimension()) {
            throw new XMLParseException("Trials dimension (" + trialsParam.getDimension()
                    + ") must equal counts dimension (" + countsParam.getDimension() + ")");
        }

        return new BinomialLikelihood(trialsParam, proportionParam, countsParam, onLogitScale);

    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newBooleanRule(ON_LOGIT_SCALE, true),
            new ElementRule(COUNTS,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(PROPORTION,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new XORRule(
                    new ElementRule(TRIALS,
                            new XMLSyntaxRule[]{AttributeRule.newIntegerArrayRule(VALUES, false),})
                    ,
                    new ElementRule(TRIALS,
                            new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}
                    )),
    };

    @Override
	public String getParserDescription() {
        return "Calculates the likelihood of some data given some parametric or empirical distribution.";
    }

    @Override
	public Class getReturnType() {
        return Likelihood.class;
    }
}
