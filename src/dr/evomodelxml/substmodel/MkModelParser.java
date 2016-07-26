/*
 * MkModelParser.java
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
 * @author Alexander V Alekseyenko (alexander.alekseyenko@gmail.com)
 *         <p/>
 *         MkModelParser implements Lewis's Mk Model Syst. Biol. 50(6):913-925, 2001
 */
public class MkModelParser extends AbstractXMLObjectParser {

    public static final String MK_SUBSTITUTION_MODEL = "mkSubstitutionModel";

    @Override
	public String getParserName() {
        return MK_SUBSTITUTION_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        XMLObject cxo = xo.getChild(GeneralSubstitutionModelParser.FREQUENCIES);
        FrequencyModel freqModel = (FrequencyModel) cxo.getChild(FrequencyModel.class);

        DataType dataType = freqModel.getDataType();
        int rateCount = ((dataType.getStateCount() - 1) * dataType.getStateCount()) / 2 - 1;
        RealParameter ratesParameter = new RealParameter.Default(rateCount, 1.0);

        Logger.getLogger("dr.evolution").info("Creating an Mk substitution model with data type: " + dataType.getType() + "on " + dataType.getStateCount() + " states.");

        int relativeTo = 1;

        return new GeneralSubstitutionModel(dataType, freqModel, ratesParameter, relativeTo);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "An Mk model of substitution. This model can also accomodate arbitrary orderings of changes.";
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