/*
 * SequenceErrorModelParser.java
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

package dr.evomodelxml.treelikelihood;

import beast.evolution.alignment.TaxonSet;
//import dr.evomodel.treelikelihood.SequenceErrorModel;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 */
public class SequenceErrorModelParser extends AbstractXMLObjectParser {

    public static final String SEQUENCE_ERROR_MODEL = "sequenceErrorModel";
    public static final String BASE_ERROR_RATE = "baseErrorRate";
    public static final String AGE_RELATED_RATE = "ageRelatedErrorRate";
    public static final String INDICATORS = "indicators";

    public static final String EXCLUDE = "exclude";
    public static final String INCLUDE = "include";

    public static final String TYPE = "type";

    @Override
	public String getParserName() {
        return SEQUENCE_ERROR_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        SequenceErrorModel.ErrorType errorType = SequenceErrorModel.ErrorType.ALL_SUBSTITUTIONS;

        if (xo.hasAttribute(TYPE)) {
            if (xo.getStringAttribute(TYPE).equalsIgnoreCase("transitions")) {
                errorType = SequenceErrorModel.ErrorType.TRANSITIONS_ONLY;
            } else if (!xo.getStringAttribute(TYPE).equalsIgnoreCase("all")) {
                throw new XMLParseException("unrecognized option for attribute, 'type': " + xo.getStringAttribute(TYPE));
            }
        }

        RealParameter baseDamageRateParameter = null;
        if (xo.hasChildNamed(BASE_ERROR_RATE)) {
            baseDamageRateParameter = (RealParameter) xo.getElementFirstChild(BASE_ERROR_RATE);
        }

        RealParameter ageRelatedRateParameter = null;
        if (xo.hasChildNamed(AGE_RELATED_RATE)) {
            ageRelatedRateParameter = (RealParameter) xo.getElementFirstChild(AGE_RELATED_RATE);
        }

        if (baseDamageRateParameter == null && ageRelatedRateParameter == null) {
            throw new XMLParseException("You must specify one or other or both of " +
                    BASE_ERROR_RATE + " and " + AGE_RELATED_RATE + " parameters");
        }

        RealParameter indicatorParameter = null;
        if (xo.hasChildNamed(INDICATORS)) {
            indicatorParameter = (RealParameter)xo.getElementFirstChild(INDICATORS);
        }


        TaxonSet includeTaxa = null;
        TaxonSet excludeTaxa = null;

        if (xo.hasChildNamed(INCLUDE)) {
            includeTaxa = (TaxonSet) xo.getElementFirstChild(INCLUDE);
        }

        if (xo.hasChildNamed(EXCLUDE)) {
            excludeTaxa = (TaxonSet) xo.getElementFirstChild(EXCLUDE);
        }

        SequenceErrorModel aDNADamageModel = new SequenceErrorModel(includeTaxa, excludeTaxa,
                errorType, baseDamageRateParameter, ageRelatedRateParameter, indicatorParameter);

        Logger.getLogger("dr.evomodel").info("Using sequence error model, assuming errors cause " +
                (errorType == SequenceErrorModel.ErrorType.TRANSITIONS_ONLY ? "transitions only." : "any substitution."));

        return aDNADamageModel;
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element returns a model that allows for post-mortem DNA damage.";
    }

    @Override
	public Class getReturnType() {
//        return SequenceErrorModel.class;
    	return null;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newStringRule(TYPE, true),
            new ElementRule(BASE_ERROR_RATE, RealParameter.class, "The base error rate per site per sequence", true),
            new ElementRule(AGE_RELATED_RATE, RealParameter.class, "The error rate per site per unit time", true),
            new ElementRule(INDICATORS, RealParameter.class, "A binary indicator of whether the sequence has errors", true),
            new XORRule(
                    new ElementRule(INCLUDE, TaxonSet.class, "A set of taxa to which to apply the damage model to"),
                    new ElementRule(EXCLUDE, TaxonSet.class, "A set of taxa to which to not apply the damage model to")
                    , true)
    };

}
