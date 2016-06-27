/*
 * OneOnXPriorParser.java
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

import beast.core.Function;
import beast.math.distributions.OneOnX;
import beast.math.distributions.Prior;
import dr.inference.model.OneOnXPrior;
import dr.inference.model.Statistic;
import dr.xml.*;

/**
 * Reads a distribution likelihood from a DOM Document element.
 */
public class OneOnXPriorParser extends AbstractXMLObjectParser {

    public static final String ONE_ONE_X_PRIOR = "oneOnXPrior";
    public static final String JEFFREYS_PRIOR = "jeffreysPrior";
    public static final String DATA = "data";

    public String getParserName() {
        return ONE_ONE_X_PRIOR;
    }

    public String[] getParserNames() {
        return new String[]{getParserName(), JEFFREYS_PRIOR};
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        OneOnX distr = new OneOnX();

        Function x = null;
        for (int j = 0; j < xo.getChildCount(); j++) {
            if (xo.getChild(j) instanceof Function) {
            	x = (Function) xo.getChild(j);
            } else {
                throw new XMLParseException("illegal element in " + xo.getName() + " element");
            }
        }

        Prior prior = new Prior();
        prior.initByName("distr", distr, "x", x);
        return prior;
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new XORRule(
                    new ElementRule(Function.class, 1, Integer.MAX_VALUE),
                    new ElementRule(DATA, new XMLSyntaxRule[]{new ElementRule(Statistic.class, 1, Integer.MAX_VALUE)})
            )
    };

    public String getParserDescription() {
        return "Calculates the (improper) prior proportional to Prod_i (1/x_i) for the given statistic x.";
    }

    public Class getReturnType() {
        return OneOnXPrior.class;
    }
}
