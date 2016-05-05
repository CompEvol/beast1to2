/*
 * GammaSiteModelParser.java
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

package dr.evomodelxml.sitemodel;


import beast.core.parameter.Parameter;
import beast.core.parameter.RealParameter;
import beast.evolution.sitemodel.SiteModel;
import beast.evolution.substitutionmodel.SubstitutionModel;
import dr.xml.*;

import java.util.logging.Logger;

/**
 */
public class GammaSiteModelParser extends AbstractXMLObjectParser {

    public static final String SUBSTITUTION_MODEL = "substitutionModel";
    public static final String MUTATION_RATE = "mutationRate";
    public static final String SUBSTITUTION_RATE = "substitutionRate";
    public static final String RELATIVE_RATE = "relativeRate";
    public static final String GAMMA_SHAPE = "gammaShape";
    public static final String GAMMA_CATEGORIES = "gammaCategories";
    public static final String PROPORTION_INVARIANT = "proportionInvariant";


    public String[] getParserNames() {
        return new String[]{
                getParserName(), "beast_" + getParserName()
        };
    }

    public String getParserName() {
        return SiteModel.class.getSimpleName();
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        SubstitutionModel substModel = (SubstitutionModel) xo.getElementFirstChild(SUBSTITUTION_MODEL);
        SiteModel siteModel = new SiteModel();
        siteModel.initByName("substModel", substModel);

        String msg = "";

        RealParameter muParam = null;
        if (xo.hasChildNamed(SUBSTITUTION_RATE)) {
            muParam = (RealParameter) xo.getElementFirstChild(SUBSTITUTION_RATE);
            muParam.setID(xo.getId());
            msg += "\n  with initial substitution rate = " + muParam.getValue();
        } else  if (xo.hasChildNamed(MUTATION_RATE)) {
            muParam = (RealParameter) xo.getElementFirstChild(MUTATION_RATE);
            muParam.setID(xo.getId());
            msg += "\n  with initial substitution rate = " + muParam.getValue();
        } else if (xo.hasChildNamed(RELATIVE_RATE)) {
            muParam = (RealParameter) xo.getElementFirstChild(RELATIVE_RATE);
            muParam.setID(xo.getId());
            msg += "\n  with initial relative rate = " + muParam.getValue();
        }
        if (muParam != null)
            siteModel.initByName("mutationRate", muParam);

//TODO Remco check the default same to BEAST 2
        RealParameter shapeParam = null;
        int catCount = 4;
        if (xo.hasChildNamed(GAMMA_SHAPE)) {
            final XMLObject cxo = xo.getChild(GAMMA_SHAPE);
            catCount = cxo.getIntegerAttribute(GAMMA_CATEGORIES);
            siteModel.gammaCategoryCount.setValue(catCount, siteModel);

            shapeParam = (RealParameter) cxo.getChild(RealParameter.class);
            siteModel.initByName("shape", shapeParam);

            msg += "\n  " + catCount + " category discrete gamma with initial shape = " + shapeParam.getValue();
        }

        RealParameter invarParam = null;
        if (xo.hasChildNamed(PROPORTION_INVARIANT)) {
            invarParam = (RealParameter) xo.getElementFirstChild(PROPORTION_INVARIANT);
            siteModel.initByName("proportionInvariant", invarParam);
            msg += "\n  initial proportion of invariant sites = " + invarParam.getValue();
        }

        Logger.getLogger("dr.evomodel").info("Creating site model." + (msg.length() > 0 ? msg : ""));

        return siteModel;
    }

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public String getParserDescription() {
        return "A SiteModel that has a gamma distributed rates across sites";
    }

    public Class getReturnType() {
        return SiteModel.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(SUBSTITUTION_MODEL, new XMLSyntaxRule[]{
                    new ElementRule(SubstitutionModel.class)
            }),
            new XORRule(
                    new XORRule(
                            new ElementRule(SUBSTITUTION_RATE, new XMLSyntaxRule[]{
                                    new ElementRule(Parameter.class)
                            }),
                            new ElementRule(MUTATION_RATE, new XMLSyntaxRule[]{
                                    new ElementRule(Parameter.class)
                            })
                    ),
                    new ElementRule(RELATIVE_RATE, new XMLSyntaxRule[]{
                            new ElementRule(Parameter.class)
                    }), true
            ),
            new ElementRule(GAMMA_SHAPE, new XMLSyntaxRule[]{
                    AttributeRule.newIntegerRule(GAMMA_CATEGORIES, true),
                    new ElementRule(Parameter.class)
            }, true),
            new ElementRule(PROPORTION_INVARIANT, new XMLSyntaxRule[]{
                    new ElementRule(Parameter.class)
            }, true)
    };
}
