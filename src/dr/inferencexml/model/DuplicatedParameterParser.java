/*
 * DuplicatedParameterParser.java
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

import dr.inference.model.Parameter;
import dr.xml.*;

/**
 */
public class DuplicatedParameterParser extends AbstractXMLObjectParser {

    public static final String DUPLICATED_PARAMETER = "duplicatedParameter";
    public static final String COPIES = "copies";

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Parameter parameter = (Parameter) xo.getChild(Parameter.class);
        XMLObject cxo = xo.getChild(COPIES);
        Parameter dup = (Parameter) cxo.getChild(Parameter.class);

        DuplicatedParameter duplicatedParameter = new DuplicatedParameter(parameter);
        duplicatedParameter.addDuplicationParameter(dup);

        return duplicatedParameter;
    */
		}

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(Parameter.class),
            new ElementRule(COPIES,
                    new XMLSyntaxRule[]{
                            new ElementRule(Parameter.class)
                    }),
    };

    @Override
	public String getParserDescription() {
        return "A duplicated parameter.";
    }

    @Override
	public Class getReturnType() {
        return Parameter.class;
    }

    @Override
	public String getParserName() {
        return DUPLICATED_PARAMETER;
    }
}
