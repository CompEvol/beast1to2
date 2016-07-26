/*
 * AsymptoticGrowthModelParser.java
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

import dr.evomodel.coalescent.LogisticGrowthModel;
import dr.evoxml.util.XMLUnits;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * Parses an element from an XMLObject into LogisticGrowthModel.
 */
public class AsymptoticGrowthModelParser extends AbstractXMLObjectParser {

    public static String ASYMPTOTE_VALUE = "asymptoteValue";
    public static String ASYMPTOTIC_GROWTH_MODEL = "asymptoticGrowth";

    public static String SHAPE = "shape";

    @Override
	public String getParserName() {
        return ASYMPTOTIC_GROWTH_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        XMLObject cxo = xo.getChild(ASYMPTOTE_VALUE);
        RealParameter asymptoticValueParam = (RealParameter) cxo.getChild(RealParameter.class);


        RealParameter rParam;

        cxo = xo.getChild(SHAPE);
        RealParameter shapeParam = (RealParameter) cxo.getChild(RealParameter.class);


        return new AsymptoticGrowthModel(asymptoticValueParam, shapeParam, units);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "Logistic growth demographic model.";
    }

    @Override
	public Class getReturnType() {
        return LogisticGrowthModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            XMLUnits.SYNTAX_RULES[0],
            new ElementRule(ASYMPTOTE_VALUE,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),


                    new ElementRule(SHAPE,
                            new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
    };
}
