/*
 * LogNormalDistributionModelParser.java
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

import beast.core.parameter.Parameter;
import beast.core.parameter.RealParameter;
import beast.math.distributions.LogNormalDistributionModel;
import dr.xml.*;

/**
 * Reads a normal distribution model from a DOM Document element.
 */
public class LogNormalDistributionModelParser extends AbstractXMLObjectParser {

    public static final String LOGNORMAL_DISTRIBUTION_MODEL = "logNormalDistributionModel";
    public static final String MEAN = "mean";
    public static final String STDEV = "stdev";
    public static final String PRECISION = "precision";
    public static final String OFFSET = "offset";
    public static final String MEAN_IN_REAL_SPACE = "meanInRealSpace";
    public static final String STDEV_IN_REAL_SPACE = "stdevInRealSpace";

    @Override
	public String getParserName() {
        return LOGNORMAL_DISTRIBUTION_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        RealParameter meanParam;

        final double offset = xo.getAttribute(OFFSET, 0.0);

        final boolean meanInRealSpace = xo.getAttribute(MEAN_IN_REAL_SPACE, false);
        final boolean stdevInRealSpace = xo.getAttribute(STDEV_IN_REAL_SPACE, false);
        if(!meanInRealSpace && stdevInRealSpace) {
            throw new RuntimeException("Cannot parameterise Lognormal model with M and Stdev");
        }


        {
            final XMLObject cxo = xo.getChild(MEAN);
            if (cxo.getChild(0) instanceof RealParameter) {
                meanParam = (RealParameter) cxo.getChild(RealParameter.class);
            } else {
            	throw new XMLParseException(beast1to2.Beast1to2Converter.NIY);
                //meanParam = new RealParameter.Default(cxo.getDoubleChild(0));
            }
        }

        {
            final XMLObject cxo = xo.getChild(PRECISION);
            
            if (cxo != null) {
            	throw new XMLParseException(beast1to2.Beast1to2Converter.NIY);
//                RealParameter precParam;
//                if (cxo.getChild(0) instanceof RealParameter) {
//                    precParam = (RealParameter) cxo.getChild(RealParameter.class);
//                } else {
//                    precParam = new RealParameter.Default(cxo.getDoubleChild(0));
//                }
//                return new LogNormalDistributionModel(meanParam, precParam, offset, meanInRealSpace,stdevInRealSpace, false);
            }
        }
        {
            final XMLObject cxo = xo.getChild(STDEV);
            RealParameter stdevParam;
            if (cxo.getChild(0) instanceof RealParameter) {
                stdevParam = (RealParameter) cxo.getChild(RealParameter.class);
            } else {
            	throw new XMLParseException(beast1to2.Beast1to2Converter.NIY);
                // stdevParam = new RealParameter.Default(cxo.getDoubleChild(0));
            }

            LogNormalDistributionModel distr = new LogNormalDistributionModel();
            distr.initByName("M", meanParam, "S", stdevParam, "offset", offset, "meanInRealSpace", meanInRealSpace);
            return distr;
        }
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newBooleanRule(MEAN_IN_REAL_SPACE, true),
            AttributeRule.newBooleanRule(STDEV_IN_REAL_SPACE, true),
            AttributeRule.newDoubleRule(OFFSET, true),
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
                    new ElementRule(PRECISION,
                            new XMLSyntaxRule[]{
                                    new XORRule(
                                            new ElementRule(RealParameter.class),
                                            new ElementRule(Double.class)
                                    )}

                    ))
    };

    @Override
	public String getParserDescription() {
        return "Describes a normal distribution with a given mean and standard deviation " +
                "that can be used in a distributionLikelihood element";
    }

    @Override
	public Class getReturnType() {
        return LogNormalDistributionModel.class;
    }
}
