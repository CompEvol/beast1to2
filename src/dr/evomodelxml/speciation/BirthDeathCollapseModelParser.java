/*
 * BirthDeathCollapseModelParser.java
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

package dr.evomodelxml.speciation;

/**
 * @author Graham Jones
 *         Date: 01/09/2013
 */


import dr.evolution.tree.Tree;
import dr.evomodel.speciation.BirthDeathCollapseModel;
import dr.evoxml.util.XMLUnits;
import beast.core.parameter.RealParameter;
import dr.xml.*;


import static dr.xml.AttributeRule.newDoubleRule;


public class BirthDeathCollapseModelParser extends AbstractXMLObjectParser {

    public static final String BIRTH_DEATH_COLLAPSE_MODEL = "birthDeathCollapseModel";

    public static final String COLLAPSE_HEIGHT = "collapseHeight";

    public static final String TREE = "speciesTree";

    public static final String BIRTHDIFF_RATE = "birthMinusDeathRate";
    public static final String RELATIVE_DEATH_RATE = "relativeDeathRate";
    public static final String ORIGIN_HEIGHT = "originHeight";
    public static final String COLLAPSE_WEIGHT = "collapseWeight";



    @Override
	public String getParserName() {
        return BIRTH_DEATH_COLLAPSE_MODEL;
    }


    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        final Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        final double collH = xo.getDoubleAttribute(COLLAPSE_HEIGHT);

        XMLObject cxo = xo.getChild(TREE);
        final Tree tree = (Tree) cxo.getChild(Tree.class);

        final RealParameter birthMinusDeath = (RealParameter) xo.getElementFirstChild(BIRTHDIFF_RATE);
        final RealParameter relativeDeathRate = (RealParameter) xo.getElementFirstChild(RELATIVE_DEATH_RATE);
        final RealParameter originHeight = (RealParameter) xo.getElementFirstChild(ORIGIN_HEIGHT);
        final RealParameter collapseWeight = (RealParameter) xo.getElementFirstChild(COLLAPSE_WEIGHT);

        final String modelName = xo.getId();

        return new BirthDeathCollapseModel(modelName, tree, units,
                birthMinusDeath, relativeDeathRate, originHeight, collapseWeight, collH);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public Class getReturnType() {
        return BirthDeathCollapseModel.class;
    }

    @Override
	public String getParserDescription() {
        return "A speciation model aimed at species delimitation, mixing birth-death model with spike near zero for node heights.";
    }


    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(TREE, new XMLSyntaxRule[]{new ElementRule(Tree.class)}),
            new ElementRule(BIRTHDIFF_RATE, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(RELATIVE_DEATH_RATE, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(ORIGIN_HEIGHT, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(COLLAPSE_WEIGHT, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            XMLUnits.SYNTAX_RULES[0],
            newDoubleRule(COLLAPSE_HEIGHT),
    };
}





