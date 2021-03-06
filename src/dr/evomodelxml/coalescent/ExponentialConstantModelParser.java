/*
 * ExponentialConstantModelParser.java
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

import dr.evomodel.coalescent.ExponentialConstantModel;
import dr.evoxml.util.XMLUnits;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * Parses an element from an DOM document into a ExponentialConstantModel.
 */
public class ExponentialConstantModelParser extends AbstractXMLObjectParser {

    public static final String EXPONENTIAL_CONSTANT_MODEL = "exponentialConstant";
    public static final String POPULATION_SIZE = "populationSize";
    public static final String TRANSITION_TIME = "transitionTime";
    public static final String GROWTH_RATE = "growthRate";

    @Override
	public String getParserName() {
        return EXPONENTIAL_CONSTANT_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        XMLObject cxo = xo.getChild(POPULATION_SIZE);
        RealParameter N0Param = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(GROWTH_RATE);
        RealParameter logGrowthParam = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(TRANSITION_TIME);
        RealParameter timeParam = (RealParameter) cxo.getChild(RealParameter.class);

        return new ExponentialConstantModel(N0Param, logGrowthParam, timeParam, units);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A demographic model of exponential growth followed by constant population size.";
    }

    @Override
	public Class getReturnType() {
        return ExponentialConstantModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            XMLUnits.SYNTAX_RULES[0],
            new ElementRule(POPULATION_SIZE,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(GROWTH_RATE,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(TRANSITION_TIME,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
    };
}
