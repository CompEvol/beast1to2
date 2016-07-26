/*
 * ProductParameterParser.java
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
import dr.xml.*;

/**
 */
public class ProductParameterParser extends AbstractXMLObjectParser {

    public static final String PRODUCT_PARAMETER = "productParameter";

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        List<RealParameter> paramList = new ArrayList<RealParameter>();
        int dim = -1;
        for (int i = 0; i < xo.getChildCount(); ++i) {
            RealParameter parameter = (RealParameter) xo.getChild(i);
            if (dim == -1) {
                dim = parameter.getDimension();
            } else {
                if (parameter.getDimension() != dim) {
                    throw new XMLParseException("All parameters in product '" + xo.getId() + "' must be the same length");
                }
            }
            paramList.add(parameter);
        }

        return new ProductParameter(paramList);
    */
		}

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(RealParameter.class,1,Integer.MAX_VALUE),
    };

    @Override
	public String getParserDescription() {
        return "A element-wise product of parameters.";
    }

    @Override
	public Class getReturnType() {
        return RealParameter.class;
    }

    @Override
	public String getParserName() {
        return PRODUCT_PARAMETER;
    }
}
