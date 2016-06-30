/*
 * GTRParser.java
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

import beast.core.parameter.RealParameter;
import beast.evolution.substitutionmodel.Frequencies;
import beast.evolution.substitutionmodel.GTR;
import dr.xml.*;

/**
 * Parses an element from an DOM document into a DemographicModel. Recognises
 * ConstantPopulation and ExponentialGrowth.
 */
public class GTRParser extends AbstractXMLObjectParser {
    public static final String GTR_MODEL = "gtrModel";

    public static final String A_TO_C = "rateAC";
    public static final String A_TO_G = "rateAG";
    public static final String A_TO_T = "rateAT";
    public static final String C_TO_G = "rateCG";
    public static final String C_TO_T = "rateCT";
    public static final String G_TO_T = "rateGT";

    public static final String FREQUENCIES = "frequencies";

    public String getParserName() {
        return GTR_MODEL;
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        XMLObject cxo = xo.getChild(FREQUENCIES);
        Frequencies freqModel = (Frequencies) cxo.getChild(Frequencies.class);

        RealParameter rateACValue = null;
        if (xo.hasChildNamed(A_TO_C)) {
            rateACValue = (RealParameter) xo.getElementFirstChild(A_TO_C);
        }
        RealParameter rateAGValue = null;
        if (xo.hasChildNamed(A_TO_G)) {
            rateAGValue = (RealParameter) xo.getElementFirstChild(A_TO_G);
        }
        RealParameter rateATValue = null;
        if (xo.hasChildNamed(A_TO_T)) {
            rateATValue = (RealParameter) xo.getElementFirstChild(A_TO_T);
        }
        RealParameter rateCGValue = null;
        if (xo.hasChildNamed(C_TO_G)) {
            rateCGValue = (RealParameter) xo.getElementFirstChild(C_TO_G);
        }
        RealParameter rateCTValue = null;
        if (xo.hasChildNamed(C_TO_T)) {
            rateCTValue = (RealParameter) xo.getElementFirstChild(C_TO_T);
        }
        RealParameter rateGTValue = null;
        if (xo.hasChildNamed(G_TO_T)) {
            rateGTValue = (RealParameter) xo.getElementFirstChild(G_TO_T);
        }
        int countNull = 0;
        if (rateACValue == null) countNull++;
        if (rateAGValue == null) countNull++;
        if (rateATValue == null) countNull++;
        if (rateCGValue == null) countNull++;
        if (rateCTValue == null) countNull++;
        if (rateGTValue == null) countNull++;

        
        if (countNull != 1)
            throw new XMLParseException("Only five parameters may be specified in GTR, leave exactly one out, the others will be specifed relative to the one left out.");
        GTR gtr = new GTR();
        gtr.initByName("frequencies", freqModel,
        		A_TO_C, rateACValue,
        		A_TO_G, rateAGValue,
        		A_TO_T, rateATValue,
        		C_TO_G, rateCGValue,
        		C_TO_T, rateCTValue,
        		G_TO_T, rateGTValue
        		);
        return gtr;
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public String getParserDescription() {
        return "A general reversible model of nucleotide sequence substitution.";
    }

    public String getExample() {

        return
                "<!-- A general time reversible model for DNA.                                          -->\n" +
                        "<!-- This element must have parameters for exactly five of the six rates               -->\n" +
                        "<!-- The sixth rate has an implied value of 1.0 and all other rates are relative to it -->\n" +
                        "<!-- This example parameterizes the rate matrix relative to the A<->G transition       -->\n" +
                        "<" + getParserName() + " id=\"gtr1\">\n" +
                        "	<" + FREQUENCIES + "> <frequencyModel idref=\"freqs\"/> </" + FREQUENCIES + ">\n" +
                        "	<" + A_TO_C + "> <parameter id=\"rateAC\" value=\"1.0\"/> </" + A_TO_C + ">\n" +
                        "	<" + A_TO_T + "> <parameter id=\"rateAT\" value=\"1.0\"/> </" + A_TO_T + ">\n" +
                        "	<" + C_TO_G + "> <parameter id=\"rateCG\" value=\"1.0\"/> </" + C_TO_G + ">\n" +
                        "	<" + C_TO_T + "> <parameter id=\"rateCT\" value=\"1.0\"/> </" + C_TO_T + ">\n" +
                        "	<" + G_TO_T + "> <parameter id=\"rateGT\" value=\"1.0\"/> </" + G_TO_T + ">\n" +
                        "</" + getParserName() + ">\n";
    }

    public Class getReturnType() {
        return GTR.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(FREQUENCIES,
                    new XMLSyntaxRule[]{new ElementRule(Frequencies.class)}),
            new ElementRule(A_TO_C,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}, true),
            new ElementRule(A_TO_G,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}, true),
            new ElementRule(A_TO_T,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}, true),
            new ElementRule(C_TO_G,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}, true),
            new ElementRule(C_TO_T,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}, true),
            new ElementRule(G_TO_T,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}, true)
    };


}
