/*
 * EmpiricalPiecewiseModelParser.java
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

import dr.evomodel.coalescent.EmpiricalPiecewiseModel;
import dr.evoxml.util.XMLUnits;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * Parses an element from an DOM document into a PiecewisePopulation.
 */
public class EmpiricalPiecewiseModelParser extends AbstractXMLObjectParser {

    public static final String EMPIRICAL_PIECEWISE = "empiricalPiecewise";
    public static final String INTERVAL_WIDTHS = "intervalWidths";
    public static final String POPULATION_SIZES = "populationSizes";
    public static final String TAU = "generationLength";
    public static final String THRESHOLD = "threshold";
    public static final String LAG = "lag";

    @Override
	public String getParserName() {
        return EMPIRICAL_PIECEWISE;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);


        XMLObject cxo = xo.getChild(INTERVAL_WIDTHS);
        double[] intervalWidths = cxo.getDoubleArrayAttribute("values");

        cxo = xo.getChild(POPULATION_SIZES);
        RealParameter popSizes = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(TAU);
        RealParameter scaleParam = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(THRESHOLD);
        RealParameter bParam = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(LAG);
        RealParameter offsetParam = (RealParameter) cxo.getChild(RealParameter.class);

        return new EmpiricalPiecewiseModel(intervalWidths, popSizes, scaleParam, bParam, offsetParam, units);
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
        return EmpiricalPiecewiseModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(INTERVAL_WIDTHS,
                    new XMLSyntaxRule[]{AttributeRule.newDoubleArrayRule("values", false),}),
            XMLUnits.SYNTAX_RULES[0],
            new ElementRule(POPULATION_SIZES,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                    "The effective population sizes of each interval."),
            new ElementRule(TAU,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                    "The scale factor."),
            new ElementRule(THRESHOLD,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                    "The threshold before counts occur."),
            new ElementRule(LAG,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},
                    "The lag between actual population sizes and genetic diversity.")
    };
}
