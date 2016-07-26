/*
 * SampleNonActiveGibbsOperatorParser.java
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

package dr.evomodelxml.coalescent.operators;

import beast.core.parameter.BooleanParameter;
import beast.core.parameter.RealParameter;
import beast.evolution.operators.SampleNonActiveGibbsOperator;
import beast.evolution.tree.coalescent.SampleOffValues;
import beast.math.distributions.ParametricDistribution;
import dr.inference.operators.MCMCOperator;
import dr.xml.*;

/**
 */
public class SampleNonActiveGibbsOperatorParser extends AbstractXMLObjectParser {
    public static String SAMPLE_NONACTIVE_GIBBS_OPERATOR = "sampleNonActiveOperator";
    public static String DISTRIBUTION = "distribution";

    public static String INDICATOR_PARAMETER = "indicators";
    public static String DATA_PARAMETER = "data";

    public String getParserName() {
        return SAMPLE_NONACTIVE_GIBBS_OPERATOR;
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        final double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);

        XMLObject cxo = xo.getChild(DISTRIBUTION);
        ParametricDistribution distribution =
                (ParametricDistribution) cxo.getChild(ParametricDistribution.class);

        cxo = xo.getChild(DATA_PARAMETER);
        RealParameter data = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(INDICATOR_PARAMETER);
        BooleanParameter indicators = (BooleanParameter) cxo.getChild(BooleanParameter.class);

        SampleOffValues operator = new SampleOffValues();
        operator.initByName("dist", distribution, "values", data, "indicators", indicators,
        		"weight", weight);
        return operator;
        
    }

    // ************************************************************************
    // AbstractXMLObjectParser implementation
    // ************************************************************************

    public String getParserDescription() {
        return "This element returns a Gibbs operator for the joint distribution of population sizes.";
    }

    public Class getReturnType() {
        return SampleOffValues.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule("distribution",
                    new XMLSyntaxRule[]{new ElementRule(ParametricDistribution.class)}),
            new ElementRule(INDICATOR_PARAMETER,
                    new XMLSyntaxRule[]{new ElementRule(BooleanParameter.class)}),
            new ElementRule(DATA_PARAMETER,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
    };

}
