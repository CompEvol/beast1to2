/*
 * LogisticGrowthModelParser.java
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

import beast.core.parameter.RealParameter;
import beast.evolution.tree.coalescent.LogisticGrowth;
import beast1to2.Beast1to2Converter;
import dr.evolution.util.Units;
import dr.evoxml.util.XMLUnits;
import dr.xml.*;

/**
 * Parses an element from an XMLObject into LogisticGrowthModel.
 */
public class LogisticGrowthModelParser extends AbstractXMLObjectParser {

    public static String POPULATION_SIZE = "populationSize";
    public static String LOGISTIC_GROWTH_MODEL = "logisticGrowth";

    public static String GROWTH_RATE = "growthRate";
    public static String DOUBLING_TIME = "doublingTime";
    public static String TIME_50 = "t50";
    public static String ALPHA = "alpha";

    public String getParserName() {
        return LOGISTIC_GROWTH_MODEL;
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        XMLObject cxo = xo.getChild(POPULATION_SIZE);
        RealParameter N0Param = (RealParameter) cxo.getChild(RealParameter.class);

        boolean usingGrowthRate = true;
        RealParameter rParam;
        if (xo.getChild(GROWTH_RATE) != null) {
            cxo = xo.getChild(GROWTH_RATE);
            rParam = (RealParameter) cxo.getChild(RealParameter.class);
        } else {
            cxo = xo.getChild(DOUBLING_TIME);
            rParam = (RealParameter) cxo.getChild(RealParameter.class);
            usingGrowthRate = false;
            throw new XMLParseException(Beast1to2Converter.NIY + " " + DOUBLING_TIME + " in " + LOGISTIC_GROWTH_MODEL);
        }

        cxo = xo.getChild(TIME_50);
        RealParameter cParam = (RealParameter) cxo.getChild(RealParameter.class);

        LogisticGrowth popfunction = new LogisticGrowth();
        popfunction.initByName("popSize", N0Param, "growthRate", rParam, "shape", cParam);
        return popfunction; // new LogisticGrowthModel(N0Param, rParam, cParam, 0.5, units, usingGrowthRate);

		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public String getParserDescription() {
        return "Logistic growth demographic model.";
    }

    public Class getReturnType() {
        return LogisticGrowth.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            XMLUnits.SYNTAX_RULES[0],
            new ElementRule(POPULATION_SIZE,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                    "This parameter represents the population size at time 0 (the time of the last tip of the tree)"),
            new XORRule(

                    new ElementRule(GROWTH_RATE,
                            new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                            "This parameter determines the rate of growth during the exponential phase. See " +
                                    "exponentialGrowth for details."),
                    new ElementRule(DOUBLING_TIME,
                            new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                            "This parameter determines the doubling time at peak growth rate.")
            ),
            new ElementRule(TIME_50,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                    "This parameter represents the time in the past when the population was half of that which it is" +
                            "at time zero (not half it's carrying capacity). It is therefore a positive number with " +
                            "the same units as divergence times. A scale operator is recommended with a starting value " +
                            "near zero. A lower bound of zero should be employed and an upper bound is recommended.")
    };
}
