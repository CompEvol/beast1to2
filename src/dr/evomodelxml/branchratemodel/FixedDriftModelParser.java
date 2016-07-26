/*
 * FixedDriftModelParser.java
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

import dr.evomodel.branchratemodel.FixedDriftModel;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * Created by mandevgill on 12/22/14.
 */
public class FixedDriftModelParser extends AbstractXMLObjectParser {
    public static final String FIXED_DRIFT = "fixedDriftModel";
    /*
    public static final String RATE_ONE = "rateOne";
    public static final String RATE_TWO = "rateTwo";
    public static final String REMAINING_RATES = "remainingRates";
    public static final String RATE_ONE_ID = "rateOneID";
    public static final String RATE_TWO_ID = "rateTwoID";
    */
    public static final String BACKBONE_DRIFT = "backboneDrift";

    public static final String OTHER_DRIFT = "otherDrift";

    public static final String BACKBONE_TAXON_LIST = "backboneTaxonList";

    @Override
	public String getParserName() {
        return FIXED_DRIFT;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        String idOne = xo.getStringAttribute(RATE_ONE_ID);

        String idTwo = xo.getStringAttribute(RATE_TWO_ID);

        RealParameter rateOne = (RealParameter) xo.getElementFirstChild(RATE_ONE);

        RealParameter rateTwo = (RealParameter) xo.getElementFirstChild(RATE_TWO);

        RealParameter remainingRates = (RealParameter) xo.getElementFirstChild(REMAINING_RATES);
        * /

        TaxonList taxonList = (TaxonList) xo.getElementFirstChild(BACKBONE_TAXON_LIST);

        Tree treeModel = (Tree) xo.getChild(Tree.class);

        RealParameter backboneDrift = (RealParameter) xo.getElementFirstChild(BACKBONE_DRIFT);

        RealParameter otherDrift = (RealParameter) xo.getElementFirstChild(OTHER_DRIFT);

        Logger.getLogger("dr.evomodel").info("Using fixed drift model.");


        return new FixedDriftModel(treeModel, backboneDrift, otherDrift, taxonList);
        //  return new FixedDriftModel(rateOne, rateTwo, remainingRates, idOne, idTwo);
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
        return FixedDriftModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new ElementRule(BACKBONE_DRIFT, RealParameter.class, "backbone drift rate", false),
            new ElementRule(OTHER_DRIFT, RealParameter.class, "other drift rate", false)
           /*
            AttributeRule.newStringRule(RATE_ONE_ID, false),
            AttributeRule.newStringRule(RATE_TWO_ID, false),
            new ElementRule(RATE_ONE, RealParameter.class, "rate one parameter", false),
            new ElementRule(RATE_TWO, RealParameter.class, "rate two parameter", false),
            new ElementRule(REMAINING_RATES, RealParameter.class, "remaining rates parameter", false)
            */
    };


}
