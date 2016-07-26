/*
 * OrnsteinUhlenbeckPriorLikelihoodParser.java
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

import dr.evomodel.coalescent.OrnsteinUhlenbeckPriorLikelihood;
import dr.evomodel.coalescent.VariableDemographicModel;
import beast.core.parameter.RealParameter;
import dr.inference.model.Statistic;
import dr.xml.*;

/**
 */
public class OrnsteinUhlenbeckPriorLikelihoodParser extends AbstractXMLObjectParser {// (todo) Parser is still in a bad state

    public static final String OU = "Ornstein-Uhlenbeck";
    public static final String LOG_SPACE = "logUnits";
    public static final String NORMALIZE = "normalize";

    public static final String DATA = "data";
    public static final String TIMES = "times";

    public static final String MEAN = "mean";
    public static final String SIGMA = "sigma";
    public static final String LAMBDA = "lambda";

    @Override
	public String getParserDescription() {
        return "";
    }

    @Override
	public Class getReturnType() {
        return OrnsteinUhlenbeckPriorLikelihood.class;
    }

    @Override
	public String getParserName() {
        return OU;
    }

    private RealParameter getParam(XMLObject xo, String name) throws XMLParseException {
        final XMLObject object = xo.getChild(name);
        // optional
        if (object == null) {
            return null;
        }
        final Object child = object.getChild(0);
        if (child instanceof RealParameter) {
            return (RealParameter) child;
        }

        double x = object.getDoubleChild(0);
        return new RealParameter(x+"");
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        RealParameter mean = getParam(xo, MEAN);
        RealParameter sigma = getParam(xo, SIGMA);
        RealParameter lambda = getParam(xo, LAMBDA);

        final boolean logSpace = xo.getAttribute(LOG_SPACE, false);
        final boolean normalize = xo.getAttribute(NORMALIZE, false);

        VariableDemographicModel m = (VariableDemographicModel) xo.getChild(VariableDemographicModel.class);

        if (m != null) {
            ParametricDistributionModel popMeanPrior = (ParametricDistributionModel) xo.getChild(ParametricDistributionModel.class);
            return new OrnsteinUhlenbeckPriorLikelihood(mean, sigma, lambda, m, logSpace, normalize, popMeanPrior);
        }

        final XMLObject cxo1 = xo.getChild(DATA);
        RealParameter dataParameter = (RealParameter) cxo1.getChild(RealParameter.class);
        final XMLObject cxo2 = xo.getChild(TIMES);
        final RealParameter timesParameter = (RealParameter) cxo2.getChild(RealParameter.class);

        return new OrnsteinUhlenbeckPriorLikelihood(mean, sigma, lambda, dataParameter, timesParameter, logSpace, normalize);
    */
		}

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {

        return new XMLSyntaxRule[]{
                AttributeRule.newBooleanRule(LOG_SPACE, true),
                AttributeRule.newBooleanRule(NORMALIZE, true),
                //new ElementRule(DATA, new XMLSyntaxRule[]{new ElementRule(Statistic.class)}),
                //new ElementRule(TIMES, new XMLSyntaxRule[]{new ElementRule(Statistic.class)}),
                new XORRule(
                        new ElementRule(MEAN, Double.class),
                        new ElementRule(MEAN, RealParameter.class),
                        true
                ),
                new XORRule(
                        new ElementRule(SIGMA, Double.class),
                        new ElementRule(SIGMA, RealParameter.class)
                ),

                new XORRule(
                        new ElementRule(LAMBDA, Double.class),
                        new ElementRule(LAMBDA, RealParameter.class),
                        true
                ),

                // you can't have a XOR (b AND c), yikes
                // make all optional and check in parser
                new ElementRule(VariableDemographicModel.class, true),

                new ElementRule(DATA, new XMLSyntaxRule[]{new ElementRule(Statistic.class)}, true),
                new ElementRule(TIMES, new XMLSyntaxRule[]{new ElementRule(Statistic.class)}, true)

        };
    }
}
