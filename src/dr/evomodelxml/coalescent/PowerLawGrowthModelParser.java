/*
 * PowerLawGrowthModelParser.java
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

import dr.evomodel.coalescent.PowerLawGrowthModel;
import dr.evoxml.util.XMLUnits;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * Parses an element from an DOM document into a ExponentialGrowth.
 */
public class PowerLawGrowthModelParser extends AbstractXMLObjectParser {

    public static String N0 = "n0";
    public static String POWER_LAW_GROWTH_MODEL = "powerLawGrowth";

    public static String POWER = "power";


    @Override
	public String getParserName() {
        return POWER_LAW_GROWTH_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        XMLObject cxo = xo.getChild(N0);
        RealParameter N0Param = (RealParameter) cxo.getChild(RealParameter.class);
        RealParameter rParam;


        cxo = xo.getChild(POWER);
        rParam = (RealParameter) cxo.getChild(RealParameter.class);
        return new PowerLawGrowthModel(N0Param, rParam, units);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A demographic model of growth according to a power law.";
    }

    @Override
	public Class getReturnType() {
        return PowerLawGrowthModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new ElementRule(N0,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),


            new ElementRule(POWER,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),

            XMLUnits.SYNTAX_RULES[0]
    };


}
