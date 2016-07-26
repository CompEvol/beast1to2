/*
 * NormalDistributionModelParser.java
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
import beast.math.distributions.Normal;
import dr.xml.*;

/**
 * Reads a normal distribution model from a DOM Document element.
 */
public class NormalDistributionModelParser extends AbstractXMLObjectParser {

    public static final String NORMAL_DISTRIBUTION_MODEL = "normalDistributionModel";
    public static final String MEAN = "mean";
    public static final String STDEV = "stdev";
    public static final String PREC = "precision";

    @Override
	public String getParserName() {
        return NORMAL_DISTRIBUTION_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        RealParameter meanParam;
        RealParameter stdevParam;
        RealParameter precParam;

        XMLObject cxo = xo.getChild(MEAN);
        if (cxo.getChild(0) instanceof RealParameter) {
            meanParam = (RealParameter) cxo.getChild(RealParameter.class);
        } else {
            meanParam = new RealParameter("0.0");
        }

        if (xo.getChild(STDEV) != null) {

            cxo = xo.getChild(STDEV);
            if (cxo.getChild(0) instanceof RealParameter) {
                stdevParam = (RealParameter) cxo.getChild(RealParameter.class);
            } else {
                stdevParam = new RealParameter(cxo.getDoubleChild(0) + "");
            }

            Normal distr = new Normal();
            distr.initByName("mean", meanParam, "sigma", stdevParam);
            return distr;
            
 //           return new NormalDistributionModel(meanParam, stdevParam);
        }

        cxo = xo.getChild(PREC);
        if (cxo.getChild(0) instanceof RealParameter) {
            precParam = (RealParameter) cxo.getChild(RealParameter.class);
        } else {
            precParam = new RealParameter(cxo.getDoubleChild(0) + "");
        }

        Normal distr = new Normal();
        distr.initByName("mean", meanParam, "tau", precParam);
        return distr;
        //return new NormalDistributionModel(meanParam, precParam, true);
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(MEAN,
                    new XMLSyntaxRule[]{
                            new XORRule(
                                    new ElementRule(RealParameter.class),
                                    new ElementRule(Double.class)
                            )}
            ),
            new XORRule(
                    new ElementRule(STDEV,
                            new XMLSyntaxRule[]{
                                    new XORRule(
                                            new ElementRule(RealParameter.class),
                                            new ElementRule(Double.class)
                                    )}
                    ),
                    new ElementRule(PREC,
                            new XMLSyntaxRule[]{
                                    new XORRule(
                                            new ElementRule(RealParameter.class),
                                            new ElementRule(Double.class)
                                    )}
                    )
            )
    };

    @Override
	public String getParserDescription() {
        return "Describes a normal distribution with a given mean and standard deviation " +
                "that can be used in a distributionLikelihood element";
    }

    @Override
	public Class getReturnType() {
        return Normal.class;
    }

}
