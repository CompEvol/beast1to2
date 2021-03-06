/*
 * ExpConstExpDemographicModelParser.java
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

import dr.evomodel.coalescent.ExpConstExpDemographicModel;
import dr.evoxml.util.XMLUnits;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * Parses an element from an DOM document into a ExponentialGrowth.
 */
public class ExpConstExpDemographicModelParser extends AbstractXMLObjectParser {

    public static final String POPULATION_SIZE = "populationSize";
    public static final String RELATIVE_PLATEAU_SIZE = "relativePlateauSize";
    public static final String RELATIVE_TIME_OF_MODERN_GROWTH = "relTimeModGrowth";
    public static final String TIME_PLATEAU = "plateauStartTime";
    public static final String ANCIENT_GROWTH_RATE = "ancientGrowthRate";

    public static final String EXP_CONST_EXP_MODEL = "expConstExp";

    @Override
	public String getParserName() {
        return EXP_CONST_EXP_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        XMLObject cxo = xo.getChild(POPULATION_SIZE);
        RealParameter N0Param = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(RELATIVE_PLATEAU_SIZE);
        RealParameter N1Param = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(RELATIVE_TIME_OF_MODERN_GROWTH);
        RealParameter rtParam = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(TIME_PLATEAU);
        RealParameter tParam = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(ANCIENT_GROWTH_RATE);
        RealParameter rParam = (RealParameter) cxo.getChild(RealParameter.class);

        return new ExpConstExpDemographicModel(N0Param, N1Param, rParam, tParam, rtParam, units);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A demographic model of exponential growth.";
    }

    @Override
	public Class getReturnType() {
        return ExpConstExpDemographicModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new ElementRule(POPULATION_SIZE,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(RELATIVE_PLATEAU_SIZE,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                    "The size of plateau relative to modern population size."),
            new ElementRule(RELATIVE_TIME_OF_MODERN_GROWTH,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                    "The time spanned by modern growth phase relative to time back to start of plateau phase."),
            new ElementRule(TIME_PLATEAU,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                    "The time of the start of plateauPhase."),
            new ElementRule(ANCIENT_GROWTH_RATE,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                    "The growth rate of early growth phase"),
            XMLUnits.SYNTAX_RULES[0]
    };

}
