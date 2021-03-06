/*
 * TwoPieceLocationScaleDistributionModelParser.java
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

import beast.core.parameter.RealParameter;
import dr.math.distributions.Distribution;
import dr.xml.*;

/**
 * Reads a normal distribution model from a DOM Document element.
 */
public class TwoPieceLocationScaleDistributionModelParser extends AbstractXMLObjectParser {

    public static final String DISTRIBUTION_MODEL = "twoPieceLocationScaleDistributionModel";
    public static final String LOCATION = "location";
    public static final String SIGMA = "sigma";
    public static final String GAMMA = "gamma";
    public static final String PARAMETERIZATION = "parameterization";

    @Override
	public String getParserName() {
        return DISTRIBUTION_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        RealParameter locationParam;
        RealParameter sigmaParam;
        RealParameter gammaParam;

        XMLObject cxo = xo.getChild(LOCATION);
        if (cxo.getChild(0) instanceof RealParameter) {
            locationParam = (RealParameter) cxo.getChild(RealParameter.class);
        } else {
            locationParam = new RealParameter.Default(cxo.getDoubleChild(0));
        }

        String parameterizationLabel = (String) xo.getAttribute(PARAMETERIZATION);
        TwoPieceLocationScaleDistributionModel.Parameterization parameterization =
                TwoPieceLocationScaleDistributionModel.Parameterization.parseFromString(parameterizationLabel);
        if (parameterization == null) {
            throw new XMLParseException("Unrecognized parameterization '" + parameterizationLabel + "'");
        }

        cxo = xo.getChild(SIGMA);
        if (cxo.getChild(0) instanceof RealParameter) {
            sigmaParam = (RealParameter) cxo.getChild(RealParameter.class);
        } else {
            sigmaParam = new RealParameter.Default(cxo.getDoubleChild(0));
        }

        cxo = xo.getChild(GAMMA);
        if (cxo.getChild(0) instanceof RealParameter) {
            gammaParam = (RealParameter) cxo.getChild(RealParameter.class);
        } else {
            gammaParam = new RealParameter.Default(cxo.getDoubleChild(0));
        }

        Distribution distribution = (Distribution) xo.getChild(Distribution.class);

        return new TwoPieceLocationScaleDistributionModel(locationParam, distribution,
                sigmaParam, gammaParam, parameterization);

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
            AttributeRule.newStringArrayRule(PARAMETERIZATION),
            new ElementRule(LOCATION,
                    new XMLSyntaxRule[]{
                            new XORRule(
                                    new ElementRule(RealParameter.class),
                                    new ElementRule(Double.class)
                            )}
            ),
            new ElementRule(SIGMA,
                    new XMLSyntaxRule[]{
                            new XORRule(
                                    new ElementRule(RealParameter.class),
                                    new ElementRule(Double.class)
                            )}, true
            ),
            new ElementRule(GAMMA,
                    new XMLSyntaxRule[]{
                            new XORRule(
                                    new ElementRule(RealParameter.class),
                                    new ElementRule(Double.class)
                            )}, true
            ),
            new ElementRule(Distribution.class),
    };

    @Override
	public String getParserDescription() {
        return "Describes a two-piece location-scale distribution with a given location and two scales " +
                "that can be used in a distributionLikelihood element";
    }

    @Override
	public Class getReturnType() {
        return TwoPieceLocationScaleDistributionModelParser.class;
    }

}
