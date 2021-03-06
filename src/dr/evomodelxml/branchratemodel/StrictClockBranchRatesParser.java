/*
 * StrictClockBranchRatesParser.java
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

import beast.core.parameter.RealParameter;
import beast.evolution.branchratemodel.StrictClockModel;

/**
 */
public class StrictClockBranchRatesParser extends AbstractXMLObjectParser {

    public static final String STRICT_CLOCK_BRANCH_RATES = "strictClockBranchRates";
    public static final String RATE = "rate";

    @Override
	public String getParserName() {
        return STRICT_CLOCK_BRANCH_RATES;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        RealParameter rateParameter = (RealParameter) xo.getElementFirstChild(RATE);

        Logger.getLogger("dr.evomodel").info("Using strict molecular clock model.");

        StrictClockModel strictClock = new StrictClockModel();
        strictClock.meanRateInput.setValue(rateParameter, strictClock);

        strictClock.initAndValidate();
        return strictClock;
	}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return
                "This element provides a strict clock model. " +
                        "All branches have the same rate of molecular evolution.";
    }

    @Override
	public Class getReturnType() {
        return StrictClockModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(RATE, RealParameter.class, "The molecular evolutionary rate parameter", false),
    };
}
