/*
 * DefaultModelParser.java
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

import dr.inference.model.DefaultModel;
import beast.core.parameter.RealParameter;
import dr.xml.AbstractXMLObjectParser;
import dr.xml.ElementRule;
import dr.xml.XMLObject;
import dr.xml.XMLSyntaxRule;

/**
 *
 */
public class DefaultModelParser extends AbstractXMLObjectParser {

    public static final String DUMMY_MODEL = "dummyModel";

    @Override
	public String getParserName() {
        return DUMMY_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        DefaultModel likelihood = new DefaultModel();

        for (int i = 0; i < xo.getChildCount(); i++) {
            RealParameter parameter = (RealParameter) xo.getChild(i);
            likelihood.addVariable(parameter);
        }

        return likelihood;
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A function wraps a component model that would otherwise not be registered with the MCMC. Always returns a log likelihood of zero.";
    }

    @Override
	public Class getReturnType() {
        return DefaultModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(RealParameter.class, 1, Integer.MAX_VALUE)
    };

}
