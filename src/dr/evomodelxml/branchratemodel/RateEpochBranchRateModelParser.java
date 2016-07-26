/*
 * RateEpochBranchRateModelParser.java
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

import dr.evomodel.branchratemodel.RateEpochBranchRateModel;
import dr.evomodelxml.tree.TreeModelParser;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 */
public class RateEpochBranchRateModelParser extends AbstractXMLObjectParser {

    public static final String RATE_EPOCH_BRANCH_RATES = "rateEpochBranchRates";
    public static final String RATE = "rate";
    public static final String EPOCH = "epoch";
    public static final String TRANSITION_TIME = "transitionTime";
    public static final String CONTINUOUS_NORMALIZATION = "continuousNormalization";

    @Override
	public String getParserName() {
        return RATE_EPOCH_BRANCH_RATES;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Logger.getLogger("dr.evomodel").info("Using multi-epoch rate model.");

        List<Epoch> epochs = new ArrayList<Epoch>();

        for (int i = 0; i < xo.getChildCount(); i++) {
            XMLObject xoc = (XMLObject) xo.getChild(i);
            if (xoc.getName().equals(EPOCH)) {
                double t = xoc.getAttribute(TRANSITION_TIME, 0.0);

                RealParameter p = (RealParameter) xoc.getChild(RealParameter.class);

                RealParameter tt = null;
                if (xoc.hasChildNamed(TRANSITION_TIME)) {
                    tt = (RealParameter) xoc.getElementFirstChild(TRANSITION_TIME);
                }
                epochs.add(new Epoch(t, p, tt));
            }
        }

        RealParameter ancestralRateParameter = (RealParameter) xo.getElementFirstChild(RATE);

        Collections.sort(epochs);
        RealParameter[] rateParameters = new RealParameter[epochs.size() + 1];
        RealParameter[] timeParameters = new RealParameter[epochs.size()];

        int i = 0;
        for (Epoch epoch : epochs) {
            rateParameters[i] = epoch.rateParameter;
            if (epoch.timeParameter != null) {
                timeParameters[i] = epoch.timeParameter;
            } else {
                timeParameters[i] = new RealParameter.Default(1);
                timeParameters[i].setParameterValue(0, epoch.transitionTime);
            }
            i++;
        }
        rateParameters[i] = ancestralRateParameter;

        if (xo.hasAttribute(CONTINUOUS_NORMALIZATION) && xo.getBooleanAttribute(CONTINUOUS_NORMALIZATION)) {
            RealParameter rootHeight = (RealParameter) xo.getChild(TreeModelParser.ROOT_HEIGHT).getChild(RealParameter.class);
            return new ContinuousEpochBranchRateModel(timeParameters, rateParameters, rootHeight);
        }

        return new RateEpochBranchRateModel(timeParameters, rateParameters);
    */
		}

    class Epoch implements Comparable {

        private final double transitionTime;
        private final RealParameter rateParameter;
        private final RealParameter timeParameter;

        public Epoch(double transitionTime, RealParameter rateParameter, RealParameter timeParameter) {
            this.transitionTime = transitionTime;
            this.rateParameter = rateParameter;
            this.timeParameter = timeParameter;
        }

        @Override
		public int compareTo(Object o) {
            return Double.compare(transitionTime, ((Epoch) o).transitionTime);
        }

    }
    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element provides a multiple epoch molecular clock model. " +
                        "All branches (or portions of them) have the same rate of molecular " +
                        "evolution within a given epoch. If parameters are used to sample " +
                        "transition times, these must be kept in ascending order by judicious " +
                        "use of bounds or priors.";
    }

    @Override
	public Class getReturnType() {
        return RateEpochBranchRateModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(EPOCH,
                    new XMLSyntaxRule[]{
                            AttributeRule.newDoubleRule(TRANSITION_TIME, true, "The time of transition between this epoch and the previous one"),
                            new ElementRule(RealParameter.class, "The evolutionary rate parameter for this epoch"),
                            new ElementRule(TRANSITION_TIME, RealParameter.class, "The transition time parameter for this epoch", true)
                    }, "An epoch that lasts until transitionTime",
                    1, Integer.MAX_VALUE
            ),
            new ElementRule(RATE, RealParameter.class, "The ancestral molecular evolutionary rate parameter", false),
            AttributeRule.newBooleanRule(CONTINUOUS_NORMALIZATION, true, "Special rate normalization for a Brownian diffusion process"),
            new ElementRule(TreeModelParser.ROOT_HEIGHT,
                    new XMLSyntaxRule[]{
                            new ElementRule(RealParameter.class, "The tree root height")
                    }, "Parameterization may require the root height", 0, 1)
    };

}
