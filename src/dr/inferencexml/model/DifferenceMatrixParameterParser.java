/*
 * DifferenceMatrixParameterParser.java
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
import dr.xml.AbstractXMLObjectParser;
import dr.xml.XMLObject;
import dr.xml.XMLParseException;
import dr.xml.XMLSyntaxRule;

public class DifferenceMatrixParameterParser extends AbstractXMLObjectParser {

    public static final String DIFFERENCE_MATRIX_PARAMETER = "differenceMatrixParameter";
    public static final String PARAMETER1 = "parameter1";
    public static final String PARAMETER2 = "parameter2";

    @Override
    public String getParserName() {
        return DIFFERENCE_MATRIX_PARAMETER;
    }

    @Override
    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        RealParameter parameter1 = (RealParameter) xo.getElementFirstChild(PARAMETER1);
        RealParameter parameter2 = (RealParameter) xo.getElementFirstChild(PARAMETER2);
//        if(parameter1.getDimension() != parameter2.getDimension()){
//            throw new XMLParseException("Parameters in ratio '" + xo.getId() + "' must have the same dimension");
//        }


        return new DifferenceMatrixParameter(parameter1, parameter2);
    */
		}//END: parseXMLObject

    @Override
    public XMLSyntaxRule[] getSyntaxRules() {
        //TODO
        return null;
    }

    @Override
    public String getParserDescription() {
        return DIFFERENCE_MATRIX_PARAMETER;
    }

    @Override
    public Class getReturnType() {
        return RealParameter.class;
    }



}//END: class
