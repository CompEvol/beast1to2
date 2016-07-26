/*
 * BooleanLikelihoodParser.java
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

package dr.inferencexml.model;

import java.util.ArrayList;
import java.util.List;

import beast.core.Distribution;
import beast.core.util.Log;
import beast.math.distributions.MRCAPrior;
import beast1to2.Beast1to2Converter;
import dr.xml.AbstractXMLObjectParser;
import dr.xml.ElementRule;
import dr.xml.XMLObject;
import dr.xml.XMLParseException;
import dr.xml.XMLSyntaxRule;

/**
 * Reads a distribution likelihood from a DOM Document element.
 */
public class BooleanLikelihoodParser extends AbstractXMLObjectParser {

    public static final String BOOLEAN_LIKELIHOOD = "booleanLikelihood";

    public static final String DATA = "data";

    public String getParserName() { return BOOLEAN_LIKELIHOOD; }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {

//        BooleanLikelihood likelihood = new BooleanLikelihood();

    	List<Distribution> distrs = new ArrayList<>();
        for (int i = 0; i < xo.getChildCount(); i++) {
            if (xo.getChild(i) instanceof Distribution) {
                distrs.add((Distribution)xo.getChild(i));
            }
        }
        if (distrs.size() > 1) {
        	Log.warning.println("Only first distribution in booleanLikelihood is used, the rest is ignored");
        }
        if (distrs.size() == 1 && !(distrs.get(0) instanceof MRCAPrior)) {
        	throw new XMLParseException(Beast1to2Converter.NIY + " " + BOOLEAN_LIKELIHOOD);
        }
        	
         return distrs.get(0);
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public String getParserDescription() {
        return "A function that log likelihood of a set of boolean statistics. "+
                "If all the statistics are true then it returns 0.0 otherwise -infinity.";
    }

    public Class getReturnType() { return Distribution.class; }

    public XMLSyntaxRule[] getSyntaxRules() { return rules; }

    private final XMLSyntaxRule[] rules = {
        new ElementRule(Distribution.class, 1, Integer.MAX_VALUE )
    };

}
