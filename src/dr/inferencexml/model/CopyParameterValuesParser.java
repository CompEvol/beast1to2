/*
 * ResetParameterParser.java
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

package dr.inferencexml.model;

import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * Resets a multi-dimensional continuous parameter.
 * 
 * @author Marc A. Suchard
 *
 */
public class CopyParameterValuesParser extends AbstractXMLObjectParser {

    public static final String RESET_PARAMETER = "copyParameterValues";
    public static final String SOURCE = "source";
    public static final String DESTINATION = "destination";

    @Override
	public String getParserName() {
        return RESET_PARAMETER;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
        return null;


//        RealParameter source = (RealParameter) xo.getChild(SOURCE).getChild(RealParameter.class);
//        RealParameter destination = (RealParameter) xo.getChild(DESTINATION).getChild(RealParameter.class);
//
//        if (source.getDimension() != destination.getDimension()) {
//            throw new XMLParseException("Source (" + source.getDimension() + ") and destination (" +
//                    destination.getDimension() + ") dimensions do not match");
//        }
//
//        for (int i = 0; i < source.getDimension(); ++i) {
//            destination.setParameterValueQuietly(i, source.getParameterValue(i));
//        }
//        destination.fireParameterChangedEvent();
//
//        return destination;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(SOURCE, new XMLSyntaxRule[] {
                    new ElementRule(RealParameter.class),
            }),
            new ElementRule(DESTINATION, new XMLSyntaxRule[] {
                    new ElementRule(RealParameter.class),
            }),
    };


    @Override
	public String getParserDescription() {
        return "Copy parameter values from source to destination";
    }

    @Override
	public Class getReturnType() {
        return RealParameter.class;
    }

}
