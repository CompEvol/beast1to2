/*
 * BirthDeathEpidemiologyModelParser.java
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

package dr.evomodelxml.speciation;

import dr.evomodel.speciation.BirthDeathSerialSamplingModel;
import dr.evoxml.util.XMLUnits;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * @author Andrew Rambaut
 * @author Tanja Stadler
 * @author Alexei Drummond
 * @author Joseph Heled
 */
public class BirthDeathEpidemiologyModelParser extends AbstractXMLObjectParser {

    public static final String BIRTH_DEATH_EPIDEMIOLOGY = "birthDeathEpidemiology";
    public static final String R0 = "R0";
    public static final String RECOVERY_RATE = "recoveryRate";
    public static final String SAMPLING_PROBABILITY = "samplingProbability";
    public static final String ORIGIN = BirthDeathSerialSamplingModelParser.ORIGIN;

    @Override
	public String getParserName() {
        return BIRTH_DEATH_EPIDEMIOLOGY;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        final String modelName = xo.getId();
        final Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        final RealParameter R0Parameter = (RealParameter) xo.getElementFirstChild(R0);
        final RealParameter recoveryRateParameter = (RealParameter) xo.getElementFirstChild(RECOVERY_RATE);
        final RealParameter samplingProbabiltyParameter = (RealParameter) xo.getElementFirstChild(SAMPLING_PROBABILITY);

        RealParameter origin = null;
        if (xo.hasChildNamed(ORIGIN)) {
            origin = (RealParameter) xo.getElementFirstChild(ORIGIN);
        }

        Logger.getLogger("dr.evomodel").info("Using epidemiological parameterization of " + getCitationRT());

        return new BirthDeathSerialSamplingModel(modelName, R0Parameter, recoveryRateParameter, samplingProbabiltyParameter, origin, units);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public static String getCitationPsiOrg() {
//        return "Stadler, T; Sampling-through-time in birth-death trees; JOURNAL OF THEORETICAL BIOLOGY (2010) 267:396-404";
        return "Stadler T (2010) J Theor Biol 267, 396-404 [Birth-Death with Serial Samples].";
    }

    public static String getCitationRT() {
        return "Stadler et al (2011) : Estimating the basic reproductive number from viral sequence data, " +
                "Mol.Biol.Evol., doi: 10.1093/molbev/msr217, 2011";
    }

    @Override
	public String getParserDescription() {
        return "Stadler et al (2011) model of epidemiology.";
    }

    @Override
	public Class getReturnType() {
        return BirthDeathSerialSamplingModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(ORIGIN, RealParameter.class, "The origin of the infection, x0 > tree.rootHeight", true),
            new ElementRule(R0, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(RECOVERY_RATE, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(SAMPLING_PROBABILITY, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            XMLUnits.SYNTAX_RULES[0]
    };
}