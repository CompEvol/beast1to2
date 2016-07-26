/*
 * RandomLocalClockModelParser.java
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


import dr.xml.*;

import java.util.logging.Logger;

import beast.core.parameter.BooleanParameter;
import beast.core.parameter.Parameter;
import beast.core.parameter.RealParameter;
import beast.evolution.branchratemodel.RandomLocalClockModel;
import beast.evolution.tree.Tree;

/**
 */
public class RandomLocalClockModelParser extends AbstractXMLObjectParser {

    public static final String LOCAL_BRANCH_RATES = "randomLocalClockModel";
    public static final String RATE_INDICATORS = "rateIndicator";
    public static final String RATES = "rates";
    public static final String CLOCK_RATE = "clockRate";
    public static final String RATES_ARE_MULTIPLIERS = "ratesAreMultipliers";

    @Override
	public String getParserName() {
        return LOCAL_BRANCH_RATES;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        Tree tree = (Tree) xo.getChild(Tree.class);

        
        Parameter<?> rateIndicatorParameter0 = (Parameter<?>) xo.getElementFirstChild(RATE_INDICATORS);
        BooleanParameter rateIndicatorParameter = null;
        for (int i = 0; i < xo.getChildCount(); i++) {
        	Object o = xo.getRawChild(i); 
        	if (o instanceof XMLObject) {
        		XMLObject xco = (XMLObject) o;
        		if (xco.getName().equals(RATE_INDICATORS)) {
        			o = xco.getRawChild(0);
        			rateIndicatorParameter = new BooleanParameter();
        			rateIndicatorParameter.initByName("dimension", Math.max(2, rateIndicatorParameter0.getDimension()),
        					"value", "0");
        			((XMLObject)o).setNativeObject(rateIndicatorParameter);
        		}
        	}
        }

        RealParameter ratesParameter = (RealParameter) xo.getElementFirstChild(RATES);
        RealParameter meanRateParameter = null;

        if (xo.hasChildNamed(CLOCK_RATE)) {
            meanRateParameter = (RealParameter) xo.getElementFirstChild(CLOCK_RATE);
        } else {
        	meanRateParameter = new RealParameter("0.0");
        }

        boolean ratesAreMultipliers = xo.getAttribute(RATES_ARE_MULTIPLIERS, false);

        Logger.getLogger("dr.evomodel").info("Using random local clock (RLC) model.");
        Logger.getLogger("dr.evomodel").info("  rates at change points are parameterized to be " +
                (ratesAreMultipliers ? " relative to parent rates." : "independent of parent rates."));

        RandomLocalClockModel clockmodel = new RandomLocalClockModel();
        clockmodel.initByName("tree", tree, "indicators", rateIndicatorParameter, "rates", ratesParameter,
        		"ratesAreMultipliers", ratesAreMultipliers, "clock.rate", meanRateParameter);
        
        return clockmodel;
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return
                "This element returns an random local clock (RLC) model." +
                        "Each branch either has a new rate or " +
                        "inherits the rate of the branch above it depending on the indicator vector, " +
                        "which is itself sampled.";
    }

    @Override
	public Class getReturnType() {
        return RandomLocalClockModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new ElementRule(Tree.class),
            new ElementRule(RATE_INDICATORS, Parameter.class, "The rate change indicators parameter", false),
            new ElementRule(RATES, Parameter.class, "The rates parameter", false),
            new ElementRule(CLOCK_RATE, Parameter.class, "The mean rate across all local clocks", true),
            AttributeRule.newBooleanRule(RATES_ARE_MULTIPLIERS, false)
    };
}
