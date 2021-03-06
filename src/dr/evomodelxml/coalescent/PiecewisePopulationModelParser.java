/*
 * PiecewisePopulationModelParser.java
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

import dr.evomodel.coalescent.PiecewisePopulationModel;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * Parses an element from an DOM document into a PiecewisePopulation.
 */
public class PiecewisePopulationModelParser extends AbstractXMLObjectParser {

    public static final String PIECEWISE_POPULATION = "piecewisePopulation";
    public static final String EPOCH_SIZES = "epochSizes";
    public static final String POPULATION_SIZE = "populationSize";
    public static final String GROWTH_RATES = "growthRates";
    public static final String EPOCH_WIDTHS = "epochWidths";

    public static final String WIDTHS = "widths";
    public static final String LINEAR = "linear";

    @Override
	public String getParserName() {
        return PIECEWISE_POPULATION;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        XMLObject obj = xo.getChild(EPOCH_WIDTHS);
        double[] epochWidths = obj.getDoubleArrayAttribute(WIDTHS);

        if (xo.hasChildNamed(EPOCH_SIZES)) {
            RealParameter epochSizes = (RealParameter) xo.getElementFirstChild(EPOCH_SIZES);

            boolean isLinear = false;
            if (xo.hasAttribute(LINEAR)) {
                isLinear = xo.getBooleanAttribute(LINEAR);
            }

            return new PiecewisePopulationModel(PIECEWISE_POPULATION, epochSizes, epochWidths, isLinear, units);
        } else {
            RealParameter populationSize = (RealParameter) xo.getElementFirstChild(POPULATION_SIZE);
            RealParameter growthRates = (RealParameter) xo.getElementFirstChild(GROWTH_RATES);
            return new PiecewisePopulationModel(PIECEWISE_POPULATION, populationSize, growthRates, epochWidths, units);
        }
    */
		}


    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element represents a piecewise population model";
    }

    @Override
	public Class getReturnType() {
        return PiecewisePopulationModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new XORRule(
                    new ElementRule(EPOCH_SIZES,
                            new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
                    new AndRule(
                            new ElementRule(POPULATION_SIZE,
                                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
                            new ElementRule(GROWTH_RATES,
                                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)})
                    )
            ),
            new ElementRule(EPOCH_WIDTHS,
                    new XMLSyntaxRule[]{AttributeRule.newDoubleArrayRule(WIDTHS)}),
            AttributeRule.newBooleanRule(LINEAR, true)
    };
}
