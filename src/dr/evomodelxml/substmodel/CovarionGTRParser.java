/*
 * CovarionGTRParser.java
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

import dr.evomodel.substmodel.AbstractCovarionDNAModel;
import dr.evomodel.substmodel.CovarionGTR;
import dr.evomodel.substmodel.FrequencyModel;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * Parses an element from an DOM document into a TwoStateCovarionModel
 */
public class CovarionGTRParser extends AbstractXMLObjectParser {
    public static final String GTR_COVARION_MODEL = "gtrCovarionModel";    

    @Override
	public String getParserName() {
        return GTR_COVARION_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        XMLObject cxo = xo.getChild(AbstractCovarionDNAModel.FREQUENCIES);
        FrequencyModel freqModel = (FrequencyModel) cxo.getChild(FrequencyModel.class);

        HiddenNucleotides dataType = (HiddenNucleotides) freqModel.getDataType();

        RealParameter hiddenRates = (RealParameter) xo.getElementFirstChild(AbstractCovarionDNAModel.HIDDEN_CLASS_RATES);
        RealParameter switchingRates = (RealParameter) xo.getElementFirstChild(AbstractCovarionDNAModel.SWITCHING_RATES);

        RealParameter rateACParameter = null;
        if (xo.hasChildNamed(GTRParser.A_TO_C)) {
            rateACParameter = (RealParameter) xo.getElementFirstChild(GTRParser.A_TO_C);
        }
        RealParameter rateAGParameter = null;
        if (xo.hasChildNamed(GTRParser.A_TO_G)) {
            rateAGParameter = (RealParameter) xo.getElementFirstChild(GTRParser.A_TO_G);
        }
        RealParameter rateATParameter = null;
        if (xo.hasChildNamed(GTRParser.A_TO_T)) {
            rateATParameter = (RealParameter) xo.getElementFirstChild(GTRParser.A_TO_T);
        }
        RealParameter rateCGParameter = null;
        if (xo.hasChildNamed(GTRParser.C_TO_G)) {
            rateCGParameter = (RealParameter) xo.getElementFirstChild(GTRParser.C_TO_G);
        }
        RealParameter rateCTParameter = null;
        if (xo.hasChildNamed(GTRParser.C_TO_T)) {
            rateCTParameter = (RealParameter) xo.getElementFirstChild(GTRParser.C_TO_T);
        }
        RealParameter rateGTParameter = null;
        if (xo.hasChildNamed(GTRParser.G_TO_T)) {
            rateGTParameter = (RealParameter) xo.getElementFirstChild(GTRParser.G_TO_T);
        }


        if (dataType != freqModel.getDataType()) {
            throw new XMLParseException("Data type of " + getParserName() + " element does not match that of its frequencyModel.");
        }

        return new CovarionGTR(
                dataType,
                hiddenRates,
                switchingRates,
                rateACParameter,
                rateAGParameter,
                rateATParameter,
                rateCGParameter,
                rateCTParameter,
                rateGTParameter,
                freqModel);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A covarion substitution model of langauge evolution with binary data and a hidden rate state with two rates.";
    }

    @Override
	public Class getReturnType() {
        return CovarionGTR.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new ElementRule(GTRParser.A_TO_C, RealParameter.class, "relative rate of A<->C substitution", true),
            new ElementRule(GTRParser.A_TO_G, RealParameter.class, "relative rate of A<->G substitution", true),
            new ElementRule(GTRParser.A_TO_T, RealParameter.class, "relative rate of A<->T substitution", true),
            new ElementRule(GTRParser.C_TO_G, RealParameter.class, "relative rate of C<->G substitution", true),
            new ElementRule(GTRParser.C_TO_T, RealParameter.class, "relative rate of C<->T substitution", true),
            new ElementRule(GTRParser.G_TO_T, RealParameter.class, "relative rate of G<->T substitution", true),
            new ElementRule(AbstractCovarionDNAModel.HIDDEN_CLASS_RATES, RealParameter.class),
            new ElementRule(AbstractCovarionDNAModel.SWITCHING_RATES, RealParameter.class),
            new ElementRule(AbstractCovarionDNAModel.FREQUENCIES, FrequencyModel.class),
    };



}
