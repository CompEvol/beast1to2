/*
 * YuleModelParser.java
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

import dr.evolution.util.Units;
import dr.evoxml.util.XMLUnits;
import dr.xml.*;

import java.util.logging.Logger;

import beast.core.parameter.RealParameter;
import beast.evolution.speciation.BirthDeathGernhard08Model;

/**
 * @author Alexei Drummond
 */
public class YuleModelParser extends AbstractXMLObjectParser {
    public static final String YULE_MODEL = "yuleModel";

    public static final String YULE = "yule";
    public static final String BIRTH_RATE = "birthRate";

    public String getParserName() {
        return YULE_MODEL;
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        final Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        final XMLObject cxo = xo.getChild(BIRTH_RATE);

        final boolean conditonalOnRoot =  xo.getAttribute(BirthDeathModelParser.CONDITIONAL_ON_ROOT, false);
        final RealParameter brParameter = (RealParameter) cxo.getChild(RealParameter.class);

        Logger.getLogger("dr.evomodel").info("Using Yule prior on tree");
        BirthDeathGernhard08Model yule = new BirthDeathGernhard08Model();
        yule.birthDiffRateParameterInput.setValue(brParameter, yule);
        yule.conditionalOnRootInput.setValue(conditonalOnRoot, yule);
        return yule;
        
//        return new BirthDeathGernhard08Model(xo.getId(), brParameter, null, null,
//                BirthDeathGernhard08Model.TreeType.UNSCALED, units, conditonalOnRoot);
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public String getParserDescription() {
        return "A speciation model of a simple constant rate Birth-death process.";
    }

    public Class getReturnType() {
        return BirthDeathGernhard08Model.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newBooleanRule(BirthDeathModelParser.CONDITIONAL_ON_ROOT, true),
            new ElementRule(BIRTH_RATE,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            XMLUnits.SYNTAX_RULES[0]
    };
}