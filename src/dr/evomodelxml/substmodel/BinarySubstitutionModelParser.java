/*
 * BinarySubstitutionModelParser.java
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

package dr.evomodelxml.substmodel;

import dr.evomodel.substmodel.FrequencyModel;
import dr.evomodel.substmodel.GeneralSubstitutionModel;
import dr.xml.*;

/**
 * @author Michael Defoin Platel
 */
public class BinarySubstitutionModelParser extends AbstractXMLObjectParser {

    public static final String BINARY_SUBSTITUTION_MODEL = "binarySubstitutionModel";

    @Override
	public String getParserName() {
        return BINARY_SUBSTITUTION_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Parameter ratesParameter;

        XMLObject cxo = xo.getChild(GeneralSubstitutionModelParser.FREQUENCIES);
        FrequencyModel freqModel = (FrequencyModel) cxo.getChild(FrequencyModel.class);

        DataType dataType = freqModel.getDataType();

        if (dataType != TwoStates.INSTANCE)
            throw new XMLParseException("Frequency model must have binary (two state) data type.");

        int relativeTo = 0;

        ratesParameter = new Parameter.Default(0);

        return new GeneralSubstitutionModel(dataType, freqModel, ratesParameter, relativeTo);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A general reversible model of sequence substitution for binary data type.";
    }

    @Override
	public Class getReturnType() {
        return GeneralSubstitutionModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new ElementRule(GeneralSubstitutionModelParser.FREQUENCIES, FrequencyModel.class),
    };

}
