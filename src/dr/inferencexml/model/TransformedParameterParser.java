/*
 * TransformedParameterParser.java
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

import beast.core.parameter.RealParameter;
import dr.inference.model.TransformedParameter;
import dr.util.Transform;
import dr.xml.*;

/**
 * @author Marc A. Suchard
 */
public class TransformedParameterParser extends AbstractXMLObjectParser {

    public static final String TRANSFORMED_PARAMETER = "transformedParameter";
    public static final String INVERSE = "inverse";

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        final RealParameter parameter = (RealParameter) xo.getChild(RealParameter.class);
        final Transform.ParsedTransform parsedTransform = (Transform.ParsedTransform) xo.getChild(Transform.ParsedTransform.class);
        final boolean inverse = xo.getAttribute(INVERSE, false);

        TransformedParameter transformedParameter = new TransformedParameter(parameter, parsedTransform.transform, inverse);
        return transformedParameter;
    */
		}

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(RealParameter.class),
            new ElementRule(Transform.ParsedTransform.class),
            AttributeRule.newBooleanRule(INVERSE, true),

    };

    @Override
	public String getParserDescription() {
        return "A transformed parameter.";
    }

    @Override
	public Class getReturnType() {
        return TransformedParameter.class;
    }

    @Override
	public String getParserName() {
        return TRANSFORMED_PARAMETER;
    }
}
