/*
 * MicrosatUpDownOperatorParser.java
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

package dr.evomodelxml.operators;

import dr.evomodel.operators.MicrosatUpDownOperator;
import beast.core.parameter.RealParameter;
import dr.inference.model.Parameter;
import dr.inference.operators.*;
import dr.inferencexml.operators.ScaleOperatorParser;
import dr.inferencexml.operators.UpDownOperatorParser;
import dr.xml.*;

/**
 */
public class MicrosatUpDownOperatorParser extends AbstractXMLObjectParser {

    public static final String MICROSAT_UP_DOWN_OPERATOR = "microsatUpDownOperator";
    public static final String UP = UpDownOperatorParser.UP;
    public static final String DOWN = UpDownOperatorParser.DOWN;

    public static final String SCALE_FACTOR = ScaleOperatorParser.SCALE_FACTOR;

    @Override
	public String getParserName() {
        return MICROSAT_UP_DOWN_OPERATOR;
    }

    private Scalable.Default[] getArgs(final XMLObject list) throws XMLParseException {
        Scalable.Default[] args = new Scalable.Default[list.getChildCount()];
        for (int k = 0; k < list.getChildCount(); ++k) {
            final Object child = list.getChild(k);
            if (child instanceof RealParameter) {
                args[k] = new Scalable.Default((Parameter) child);
            }

        }
        return args;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        final double scaleFactor = xo.getDoubleAttribute(SCALE_FACTOR);

        final double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);

        final CoercionMode mode = CoercionMode.parseMode(xo);

        final Scalable.Default[] upArgs = getArgs(xo.getChild(UP));
        final Scalable.Default[] dnArgs = getArgs(xo.getChild(DOWN));

        return new MicrosatUpDownOperator(upArgs, dnArgs, scaleFactor, weight, mode);
    */
		}

    @Override
	public String getParserDescription() {
        return "This element represents an operator that scales two parameters in different directions. " +
                "Each operation involves selecting a scale uniformly at random between scaleFactor and 1/scaleFactor. " +
                "The up parameter is multipled by this scale and the down parameter is divided by this scale.";
    }

    @Override
	public Class getReturnType() {
        return MicrosatUpDownOperator.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] ee = {
            new ElementRule(RealParameter.class, true)
    };

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newDoubleRule(SCALE_FACTOR),
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
            AttributeRule.newBooleanRule(CoercableMCMCOperator.AUTO_OPTIMIZE, true),

            // Allow an arbitrary number of Parameters or Scalables in up or down
            new ElementRule(UP, ee, 1, Integer.MAX_VALUE),
            new ElementRule(DOWN, ee, 1, Integer.MAX_VALUE),
    };
}
