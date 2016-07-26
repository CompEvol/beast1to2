/*
 * DistributionModelParser.java
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

package dr.inferencexml.distribution;

import beast.core.parameter.RealParameter;
import beast.math.distributions.ParametricDistribution;
import dr.xml.*;

/**
 */
public abstract class DistributionModelParser extends AbstractXMLObjectParser {

    public static final String OFFSET = "offset";
    public static final String MEAN = "mean";
    public static final String SHAPE = "shape";
    public static final String SCALE = "scale";
    public static final String RATE = "rate";

    /**
     * @param parameters an array of the parsed parameters, in order of the getParameterNames() array.
     * @param offset     the parsed offset
     * @return a distribution model constructed from provided parameters and offset
     */
    abstract ParametricDistribution parseDistributionModel(RealParameter[] parameters, double offset);

    /**
     * @return a list of xml element names for parameters of this distribution.
     */
    abstract String[] getParameterNames();

    abstract boolean allowOffset();

    @Override
	public final Object parseXMLObject(XMLObject xo) throws XMLParseException {

        double offset = xo.getAttribute(OFFSET, 0.0);

        String[] names = getParameterNames();
        RealParameter[] parameters = new RealParameter[names.length];
        for (int i = 0; i < names.length; i++) {
            parameters[i] = getParameter(xo, names[i]);
        }

        return parseDistributionModel(parameters, offset);
		}

    private RealParameter getParameter(XMLObject xo, String parameterName) throws XMLParseException {
        final XMLObject cxo = xo.getChild(parameterName);
        return cxo.getChild(0) instanceof RealParameter ?
                (RealParameter) cxo.getChild(RealParameter.class) : new RealParameter(cxo.getDoubleChild(0) +"");
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {

        String[] names = getParameterNames();

        XMLSyntaxRule[] rules = new XMLSyntaxRule[names.length + (allowOffset() ? 1 : 0)];
        for (int i = 0; i < names.length; i++) {
            rules[i] = new XORRule(
                    new ElementRule(names[i], Double.class),
                    new ElementRule(names[i], RealParameter.class)
            );
        }
        if (allowOffset()) {
            rules[rules.length - 1] = AttributeRule.newDoubleRule(OFFSET, true);
        }

        return rules;
    }

}
