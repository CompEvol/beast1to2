/*
 * ExponentialStatisticParser.java
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

import dr.inference.model.ExponentialStatistic;
import dr.inference.model.Statistic;
import dr.xml.*;

/**
 */
public class ExponentialStatisticParser extends AbstractXMLObjectParser {

    public static String EXPONENTIAL_STATISTIC = "exponentialStatistic";
    public static String EXP = "exp";

    @Override
	public String[] getParserNames() {
        return new String[]{getParserName(), EXP};
    }

    @Override
	public String getParserName() {
        return EXPONENTIAL_STATISTIC;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        ExponentialStatistic expStatistic = null;

        Object child = xo.getChild(0);
        if (child instanceof Statistic) {
            expStatistic = new ExponentialStatistic(EXPONENTIAL_STATISTIC, (Statistic) child);
        } else {
            throw new XMLParseException("Unknown element found in " + getParserName() + " element:" + child);
        }

        return expStatistic;
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element returns a statistic that is the element-wise exponentiation of the child statistic.";
    }

    @Override
	public Class getReturnType() {
        return ExponentialStatistic.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new ElementRule(Statistic.class, 1, 1)
    };
}
