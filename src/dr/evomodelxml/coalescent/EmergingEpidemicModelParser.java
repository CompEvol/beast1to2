/*
 * EmergingEpidemicModelParser.java
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

import dr.evolution.tree.Tree;
import dr.evomodel.coalescent.EmergingEpidemicModel;
import dr.evoxml.util.XMLUnits;
import dr.inference.model.Parameter;
import dr.xml.*;

/**
 * Parser for the EmergingEpidemic exponential model.
 */
public class EmergingEpidemicModelParser extends AbstractXMLObjectParser {

    public static String EMERGING_EPIDEMIC_MODEL = "emergingEpidemic";

    // the parameters
    public static String GROWTH_RATE = "growthRate";
    public static String GENERATION_TIME = "generationTime";
    public static String GENERATION_DISTRIBUTION_SHAPE = "generationShape";
    public static String OFFSPRING_DISPERSION = "offspringDispersion";

    public static String TREE = "epidemicTree";


    @Override
	public String getParserName() {
        return EMERGING_EPIDEMIC_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        Parameter growthRateParameter =  (Parameter)xo.getElementFirstChild(GROWTH_RATE);
        Parameter generationTimeParameter = (Parameter)xo.getElementFirstChild(GENERATION_TIME);
        Parameter generationShapeParameter = (Parameter)xo.getElementFirstChild(GENERATION_DISTRIBUTION_SHAPE);
        Parameter offspringDispersionParameter = (Parameter)xo.getElementFirstChild(OFFSPRING_DISPERSION);
        TreeModel tree = (TreeModel)xo.getElementFirstChild(TREE);

        return new EmergingEpidemicModel(growthRateParameter, generationTimeParameter, generationShapeParameter, offspringDispersionParameter, tree, units);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A self-consistent model of emerging epidemics.";
    }

    @Override
	public Class getReturnType() {
        return EmergingEpidemicModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new ElementRule(GROWTH_RATE, Parameter.class),
            new ElementRule(GENERATION_TIME, Parameter.class),
            new ElementRule(GENERATION_DISTRIBUTION_SHAPE, Parameter.class),
            new ElementRule(OFFSPRING_DISPERSION, Parameter.class),
            new ElementRule(TREE, Tree.class),

            XMLUnits.SYNTAX_RULES[0]
    };


}
