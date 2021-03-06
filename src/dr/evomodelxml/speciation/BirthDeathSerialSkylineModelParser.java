/*
 * BirthDeathSerialSkylineModelParser.java
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
 * @author Alexei Drummond
 */
public class BirthDeathSerialSkylineModelParser extends AbstractXMLObjectParser {

    public static final String BIRTH_DEATH_SKYLINE_MODEL = "birthDeathSkyline";
    public static final String TIMES = "times";
    public static final String LAMBDA = "birthRate";
    public static final String MU = "deathRate";
    public static final String RELATIVE_MU = "relativeDeathRate";
    public static final String PSI = "psi";
    public static final String SAMPLE_PROBABILITY = "sampleProbability";
    public static final String SAMPLE_BECOMES_NON_INFECTIOUS = "sampleBecomesNonInfectiousProb";
    public static final String TIMES_START_FROM_ORIGIN = "timesStartFromOrigin";
    public static final String R = "r";
    public static final String ORIGIN = "origin";
    public static final String TREE_TYPE = "type";

    @Override
	public String getParserName() {
        return BIRTH_DEATH_SKYLINE_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        final String modelName = xo.getId();
        final Units.Type units = XMLUnits.Utils.getUnitsAttr(xo);

        final RealParameter times = (RealParameter) xo.getElementFirstChild(TIMES);
        final RealParameter lambda = (RealParameter) xo.getElementFirstChild(LAMBDA);


        boolean relativeDeath = xo.hasChildNamed(RELATIVE_MU);
        RealParameter mu;
        if (relativeDeath) {
            mu = (RealParameter) xo.getElementFirstChild(RELATIVE_MU);
        } else {
            mu = (RealParameter) xo.getElementFirstChild(MU);
        }

        final RealParameter psi = (RealParameter) xo.getElementFirstChild(PSI);
        final RealParameter p = (RealParameter) xo.getElementFirstChild(SAMPLE_PROBABILITY);

        final boolean timesStartFromOrigin = xo.getAttribute(TIMES_START_FROM_ORIGIN, false);

        RealParameter origin = null;
        if (xo.hasChildNamed(ORIGIN)) {
            origin = (RealParameter) xo.getElementFirstChild(ORIGIN);
        }

        final RealParameter r = xo.hasChildNamed(SAMPLE_BECOMES_NON_INFECTIOUS) ?
                (RealParameter) xo.getElementFirstChild(SAMPLE_BECOMES_NON_INFECTIOUS) : new RealParameter.Default(0.0);
//        r.setParameterValueQuietly(0, 1 - r.getParameterValue(0)); // donot use it, otherwise log is changed improperly

        Logger.getLogger("dr.evomodel").info(xo.hasChildNamed(SAMPLE_BECOMES_NON_INFECTIOUS) ? getCitationRT() : getCitationPsiOrg());

        return new BirthDeathSerialSkylineModel(modelName, times, lambda, mu, psi, p, origin, relativeDeath,
                false, timesStartFromOrigin, units);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public static String getCitationPsiOrg() {
//        return "Stadler, T; Sampling-through-time in birth-death trees; JOURNAL OF THEORETICAL BIOLOGY (2010) 267:396-404";
        return "Stadler T et al (2011, in prep)";
    }

    public static String getCitationRT() {
        return "Stadler T et al (2011, in prep)";
    }

    @Override
	public String getParserDescription() {
        return "Stadler T et al (2011, in prep)";
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
            AttributeRule.newStringRule(TREE_TYPE, true),
            AttributeRule.newBooleanRule(TIMES_START_FROM_ORIGIN, false, "if true, then the time vector represents the " +
                    "epoch widths in times starting from the origin and moving tipwards. " +
                    "Note that the birth/death/sampling rate vectors still specify the parameters starting from tips " +
                    "and moving to root."),
            new ElementRule(ORIGIN, RealParameter.class, "The origin of the infection, x0 > tree.rootHeight", true),
            new ElementRule(TIMES, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(LAMBDA, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new XORRule(
                    new ElementRule(MU, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
                    new ElementRule(RELATIVE_MU, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)})),
            new ElementRule(PSI, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(SAMPLE_BECOMES_NON_INFECTIOUS, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}, true),
            new ElementRule(SAMPLE_PROBABILITY, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            XMLUnits.SYNTAX_RULES[0]
    };
}