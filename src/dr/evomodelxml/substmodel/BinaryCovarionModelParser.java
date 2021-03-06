/*
 * BinaryCovarionModelParser.java
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

import dr.evomodel.substmodel.BinaryCovarionModel;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * Parses an element from an DOM document into a TwoStateCovarionModel
 */
public class BinaryCovarionModelParser extends AbstractXMLObjectParser {
    public static final String COVARION_MODEL = "binaryCovarionModel";
    public static final String ALPHA = "alpha";
    public static final String SWITCHING_RATE = "switchingRate";
    public static final String FREQUENCIES = "frequencies";
    public static final String HIDDEN_FREQUENCIES = "hiddenFrequencies";
    public static final String VERSION = "version";
    public static final BinaryCovarionModel.Version DEFAULT_VERSION = BinaryCovarionModel.Version.VERSION1;

    @Override
	public String getParserName() {
        return COVARION_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        RealParameter alphaParameter;
        RealParameter switchingRateParameter;

        XMLObject cxo = xo.getChild(FREQUENCIES);
        RealParameter frequencies = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(HIDDEN_FREQUENCIES);
        RealParameter hiddenFrequencies = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(ALPHA);
        alphaParameter = (RealParameter) cxo.getChild(RealParameter.class);

        // alpha must be positive and less than 1.0 because the fast rate is normalized to 1.0
        alphaParameter.addBounds(new RealParameter.DefaultBounds(1.0, 0.0, 1));
        hiddenFrequencies.addBounds(new RealParameter.DefaultBounds(1.0, 0.0, hiddenFrequencies.getDimension()));
        frequencies.addBounds(new RealParameter.DefaultBounds(1.0, 0.0, frequencies.getDimension()));

        cxo = xo.getChild(SWITCHING_RATE);
        switchingRateParameter = (RealParameter) cxo.getChild(RealParameter.class);

        BinaryCovarionModel.Version version = DEFAULT_VERSION;
        if (xo.hasAttribute(VERSION)) {
            version = BinaryCovarionModel.Version.parseFromString(xo.getStringAttribute(VERSION));
        }

        BinaryCovarionModel model = new BinaryCovarionModel(TwoStateCovarion.INSTANCE,
                frequencies, hiddenFrequencies, alphaParameter, switchingRateParameter, version);

        System.out.println(model);

        return model;
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A covarion substitution model on binary data and a hidden rate state with two rates.";
    }

    @Override
	public Class getReturnType() {
        return BinaryCovarionModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(FREQUENCIES, RealParameter.class),
            new ElementRule(HIDDEN_FREQUENCIES, RealParameter.class),
            new ElementRule(ALPHA,
                    new XMLSyntaxRule[]{
                            new ElementRule(RealParameter.class, true)}
            ),
            new ElementRule(SWITCHING_RATE,
                    new XMLSyntaxRule[]{
                            new ElementRule(RealParameter.class, true)}
            ),
            AttributeRule.newStringRule(VERSION, true),
    };


}
