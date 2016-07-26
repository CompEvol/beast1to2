/*
 * RelaxedDriftModelParser.java
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

import dr.evomodel.branchratemodel.RelaxedDriftModel;
import dr.evomodel.tree.TreeModel;
import dr.inference.model.Parameter;
import dr.xml.*;

/**
 * Created by IntelliJ IDEA.
 * User: mandevgill
 * Date: 7/28/14
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class RelaxedDriftModelParser extends AbstractXMLObjectParser {

    public static final String RELAXED_DRIFT = "relaxedDriftModel";
    public static final String RATES = "rates";
    public static final String RATE_IND = "rateIndicator";
    public static final String DRIFT_RATES = "driftRates";

    @Override
	public String getParserName() {
        return RELAXED_DRIFT;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        TreeModel tree = (TreeModel) xo.getChild(TreeModel.class);

        Parameter ratesParameter = (Parameter) xo.getElementFirstChild(RATES);

        Parameter rateIndicatorParameter = (Parameter) xo.getElementFirstChild(RATE_IND);

        Parameter driftRates = null;
        if (xo.hasChildNamed(DRIFT_RATES)) {
            driftRates = (Parameter) xo.getElementFirstChild(DRIFT_RATES);
        }


        Logger.getLogger("dr.evomodel").info("Using relaxed drift model.");


        return new RelaxedDriftModel(tree, rateIndicatorParameter,
                ratesParameter, driftRates);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return
                "This element returns a relaxed drift model.";
    }

    @Override
	public Class getReturnType() {
        return RelaxedDriftModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new ElementRule(TreeModel.class),
            new ElementRule(RATES, Parameter.class, "The rates parameter", false),
            new ElementRule(RATE_IND, Parameter.class, "The indicator parameter", false),
            new ElementRule(DRIFT_RATES, Parameter.class, "the drift rates parameter", true)
    };
}
