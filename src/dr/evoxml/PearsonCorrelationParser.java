/*
 * PearsonCorrelationParser.java
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

package dr.evoxml;

import dr.xml.*;
import beast.core.parameter.RealParameter;
import dr.inference.model.PearsonCorrelation;

/**
 * @author Simon Greenhill
 */

public class PearsonCorrelationParser extends AbstractXMLObjectParser {

    public static final String PEARSON_CORRELATION = "pearsonCorrelation";
    public static final String LOG = "log";

    @Override
	public String getParserName() { return PEARSON_CORRELATION; }
                                             
    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        boolean log = xo.getAttribute(LOG, false);
        RealParameter X = (RealParameter)xo.getChild(0);
        RealParameter Y = (RealParameter)xo.getChild(1);

        // System.out.println("Correlating " + X + " with " + Y + " using log = " + log);
        PearsonCorrelation pearsonCorrelation = new PearsonCorrelation(X, Y, log);
        return pearsonCorrelation;

    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A Pearson Correlation between two Parameters";
    }

    @Override
	public String getExample() {
        return "<pearsonCorrelation id=\"r\" log=\"true\">\n"+
               "	<parameter idref=\"param1\"/>\n"+
               "    <parameter idref=\"param2\"/>\n"+
               "</pearsonCorrelation>\n";
    }

    @Override
	public Class getReturnType() { return PearsonCorrelation.class; }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() { return rules; }

    private final XMLSyntaxRule[] rules = {
        // There should be two and only two Parameters (X & Y)
        new ElementRule(RealParameter.class, 2, 2),
        // the optional log attribute has to be a Boolean
        AttributeRule.newBooleanRule(LOG, true),
    };

}
