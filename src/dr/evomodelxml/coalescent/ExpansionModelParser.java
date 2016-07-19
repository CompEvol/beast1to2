/*
 * ExpansionModelParser.java
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
import beast.evolution.tree.coalescent.Expansion;
import beast.evolution.tree.coalescent.ExponentialGrowth;
import beast1to2.Beast1to2Converter;
import dr.evolution.util.Units;
import dr.evoxml.util.XMLUnits;
import dr.xml.*;

/**
 * Parses an element from an DOM document into a ConstantExponentialModel.
 */
public class ExpansionModelParser extends AbstractXMLObjectParser {

    public static final String EXPANSION_MODEL = "expansion";
    public static final String POPULATION_SIZE = "populationSize";
    public static final String ANCESTRAL_POPULATION_PROPORTION = "ancestralPopulationProportion";

    public static final String GROWTH_RATE = "growthRate";
    public static final String DOUBLING_TIME = "doublingTime";

    public String getParserName() {
        return EXPANSION_MODEL;
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        XMLObject cxo = xo.getChild(POPULATION_SIZE);
        RealParameter N0Param = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(ANCESTRAL_POPULATION_PROPORTION);
        RealParameter N1Param = (RealParameter) cxo.getChild(RealParameter.class);

        RealParameter rParam;
        boolean usingGrowthRate = true;

        if (xo.getChild(GROWTH_RATE) != null) {
            cxo = xo.getChild(GROWTH_RATE);
            rParam = (RealParameter) cxo.getChild(RealParameter.class);
        } else {
            cxo = xo.getChild(DOUBLING_TIME);
            rParam = (RealParameter) cxo.getChild(RealParameter.class);
            usingGrowthRate = false;
            throw new XMLParseException(Beast1to2Converter.NIY + " " + DOUBLING_TIME + " in " + EXPANSION_MODEL);
        }

        Expansion popfunction = new Expansion();
        popfunction.initByName("popSize", N0Param, "growthRate", rParam, "ancestralPopulationProportion", N1Param);
        return popfunction;
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public String getParserDescription() {
        return "A demographic model of constant population size followed by exponential growth.";
    }

    public Class getReturnType() {
        return Expansion.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            XMLUnits.SYNTAX_RULES[0],
            new ElementRule(POPULATION_SIZE,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(ANCESTRAL_POPULATION_PROPORTION,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new XORRule(

                    new ElementRule(GROWTH_RATE,
                            new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                            "A value of zero represents a constant population size, negative values represent decline towards the present, " +
                                    "positive numbers represents exponential growth towards the present. " +
                                    "A random walk operator is recommended for this parameter with a starting value of 0.0 and no upper or lower limits."),
                    new ElementRule(DOUBLING_TIME,
                            new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                            "This parameter determines the doubling time.")
            )
    };

}
