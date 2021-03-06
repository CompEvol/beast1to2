/*
 * CompoundSymmetricMatrixParser.java
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

import dr.inference.model.CompoundSymmetricMatrix;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 *
 */
public class CompoundSymmetricMatrixParser extends AbstractXMLObjectParser {

    public final static String MATRIX_PARAMETER = "compoundSymmetricMatrix";
    public static final String DIAGONAL = "diagonal";
    public static final String OFF_DIAGONAL = "offDiagonal";
    public static final String AS_CORRELATION = "asCorrelation";

    @Override
	public String getParserName() {
        return MATRIX_PARAMETER;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        XMLObject cxo = xo.getChild(DIAGONAL);
        RealParameter diagonalParameter = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(OFF_DIAGONAL);
        RealParameter offDiagonalParameter = (RealParameter) cxo.getChild(RealParameter.class);

        boolean asCorrelation = xo.getAttribute(AS_CORRELATION, false);

        return new CompoundSymmetricMatrix(diagonalParameter, offDiagonalParameter, asCorrelation);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A diagonal matrix parameter constructed from its diagonals.";
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new ElementRule(DIAGONAL,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(OFF_DIAGONAL,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            AttributeRule.newBooleanRule(AS_CORRELATION, true)
    };

    @Override
	public Class getReturnType() {
        return CompoundSymmetricMatrix.class;
    }
}
