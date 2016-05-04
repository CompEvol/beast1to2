/*
 * ConstantPopulationModelParser.java
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
import beast.evolution.tree.coalescent.ConstantPopulation;

import dr.evolution.util.Units;
import dr.evoxml.util.XMLUnits;
import dr.xml.*;


/**
 * Parses an element from an DOM document into a ConstantPopulation.
 */
public class ConstantPopulationModelParser extends AbstractXMLObjectParser {

    public static String CONSTANT_POPULATION_MODEL = "constantSize";
    public static String POPULATION_SIZE = "populationSize";

    public String getParserName() {
        return CONSTANT_POPULATION_MODEL;
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);
        XMLObject cxo = xo.getChild(POPULATION_SIZE);
        RealParameter N0Param = (RealParameter) cxo.getChild(RealParameter.class);

        ConstantPopulation cp = new ConstantPopulation();
        cp.popSizeParameter.setValue(N0Param.getValue(), cp);

        return cp;

		/*
        Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        XMLObject cxo = xo.getChild(POPULATION_SIZE);
        Parameter N0Param = (Parameter) cxo.getChild(Parameter.class);

        return new ConstantPopulationModel(N0Param, units);
    */
		}


    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public String getParserDescription() {
        return "A demographic model representing a constant population size through time.";
    }

    public Class getReturnType() {
        return ConstantPopulation.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            XMLUnits.UNITS_RULE,
            new ElementRule(POPULATION_SIZE,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)})
    };

}
