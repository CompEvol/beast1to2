/*
 * ComplexSubstitutionModelParser.java
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

import dr.evolution.datatype.DataType;
import dr.evomodel.substmodel.FrequencyModel;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 */
public class ComplexSubstitutionModelParser extends AbstractXMLObjectParser {

    public static final String COMPLEX_SUBSTITUTION_MODEL = "complexSubstitutionModel";
    public static final String RATES = "rates";
    public static final String ROOT_FREQUENCIES = "rootFrequencies";
    public static final String INDICATOR = "rateIndicator";
    public static final String RANDOMIZE = "randomizeIndicator";
    public static final String NORMALIZATION = "normalize";
    public static final String MAX_CONDITION_NUMBER = "maxConditionNumber";
    public static final String CONNECTED = "mustBeConnected";
    public static final String MAX_ITERATIONS = "maxIterations";
    public static final String CHECK_CONDITIONING = "checkConditioning";

    @Override
	public String[] getParserNames() {
        return new String[]{
                getParserName(), "beast_" + getParserName()
        };
    }

    @Override
	public String getParserName() {
        return COMPLEX_SUBSTITUTION_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        DataType dataType = DataTypeUtils.getDataType(xo);

        if (dataType == null) dataType = (DataType) xo.getChild(DataType.class);

        XMLObject cxo = xo.getChild(RATES);

        RealParameter ratesParameter = (RealParameter) cxo.getChild(RealParameter.class);

        int rateCount = (dataType.getStateCount() - 1) * dataType.getStateCount();

        if (ratesParameter.getDimension() != rateCount) {
            throw new XMLParseException("Rates parameter in " + getParserName() + " element should have " + (rateCount)
                    + " dimensions.  However parameter dimension is " + ratesParameter.getDimension());
        }


        cxo = xo.getChild(ROOT_FREQUENCIES);
        FrequencyModel rootFreq = (FrequencyModel) cxo.getChild(FrequencyModel.class);

        if (dataType != rootFreq.getDataType()) {
            throw new XMLParseException("Data type of " + getParserName() + " element does not match that of its rootFrequencyModel.");
        }

        RealParameter indicators = null;

        if (xo.hasChildNamed(INDICATOR)) {
            indicators = (RealParameter) ((XMLObject) xo.getChild(INDICATOR)).getChild(RealParameter.class);
            if (ratesParameter.getDimension() != indicators.getDimension())
                throw new XMLParseException("Rate parameter dimension must match indicator parameter dimension");
        }

        StringBuffer sb = new StringBuffer().append("Constructing a complex substitution model using\n")
                .append("\tRate parameters: ").append(ratesParameter.getId())
                .append("\n").append("\tRoot frequency model: ").append(rootFreq.getId()).append("\n");

        ComplexSubstitutionModel model;

        if (indicators == null)
            model = new ComplexSubstitutionModel(xo.getId(), dataType, rootFreq, ratesParameter);
        else {

            boolean randomize = xo.getAttribute(RANDOMIZE, false);
            boolean connected = xo.getAttribute(CONNECTED, false);
            model = new SVSComplexSubstitutionModel(xo.getId(), dataType, rootFreq, ratesParameter, indicators);
            if (randomize) {
                BayesianStochasticSearchVariableSelection.Utils.randomize(indicators,
                        dataType.getStateCount(), false);

                boolean valid = !Double.isInfinite(model.getLogLikelihood());
                if (!valid) {
                    throw new XMLParseException("Poor tolerance in complex substitution model.  Please retry analysis using BEAGLE");
                }
            }
            sb.append("\tBSSVS indicators: ").append(indicators.getId()).append("\n");
            sb.append("\tGraph must be connected: ").append(connected).append("\n");
        }

        boolean doNormalization = xo.getAttribute(NORMALIZATION, true);
        model.setNormalization(doNormalization);
        sb.append("\tNormalized: ").append(doNormalization).append("\n");

        boolean checkConditioning = xo.getAttribute(CHECK_CONDITIONING, true);
        model.setCheckConditioning(checkConditioning);

        if (checkConditioning) {
            double maxConditionNumber = xo.getAttribute(MAX_CONDITION_NUMBER, 1000);
            model.setMaxConditionNumber(maxConditionNumber);
            sb.append("\tMax. condition number: ").append(maxConditionNumber).append("\n");
        }

        int maxIterations = xo.getAttribute(MAX_ITERATIONS, 1000);
        model.setMaxIterations(maxIterations);
        sb.append("\tMax iterations: ").append(maxIterations).append("\n");

        sb.append("\t\tPlease cite Edwards, Suchard et al. (2011)\n");

        Logger.getLogger("dr.evomodel.substmodel").info(sb.toString());
        return model;
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A general reversible model of sequence substitution for any data type with stochastic variable selection.";
    }

    @Override
	public Class getReturnType() {
        return ComplexSubstitutionModelParser.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new XORRule(
                    new StringAttributeRule(DataType.DATA_TYPE, "The type of sequence data",
                            DataType.getRegisteredDataTypeNames(), false),
                    new ElementRule(DataType.class)
            ),
            AttributeRule.newBooleanRule(RANDOMIZE, true),
            new ElementRule(ROOT_FREQUENCIES, FrequencyModel.class),
            new ElementRule(RATES,
                    new XMLSyntaxRule[]{
                            new ElementRule(RealParameter.class)}
            ),
            new ElementRule(INDICATOR,
                    new XMLSyntaxRule[]{
                            new ElementRule(RealParameter.class)
                    }, true),
            AttributeRule.newBooleanRule(NORMALIZATION, true),
            AttributeRule.newDoubleRule(MAX_CONDITION_NUMBER, true),
            AttributeRule.newBooleanRule(CONNECTED, true),
            AttributeRule.newIntegerRule(MAX_ITERATIONS, true),
            AttributeRule.newBooleanRule(CHECK_CONDITIONING, true),
    };

}
