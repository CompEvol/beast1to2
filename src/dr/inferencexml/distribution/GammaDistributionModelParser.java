/*
 * GammaDistributionModelParser.java
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

import beast.core.Distribution;
import beast.core.parameter.Parameter;
import beast.core.parameter.RealParameter;
import beast.math.distributions.Gamma;
import dr.inference.distribution.GammaDistributionModel;
import dr.xml.*;

public class GammaDistributionModelParser extends AbstractXMLObjectParser {

    public static final String MEAN = "mean";
    public static final String SHAPE = "shape";
    public static final String SCALE = "scale";
    public static final String RATE = "rate";
    public static final String OFFSET = "offset";

    @Override
	public String getParserName() {
        return GammaDistributionModel.GAMMA_DISTRIBUTION_MODEL;
    }

    private RealParameter getParameterOrValue(String name, XMLObject xo) throws XMLParseException {
        RealParameter parameter;
        if (xo.hasChildNamed(name)) {
            XMLObject cxo = xo.getChild(name);

            parameter = (RealParameter)cxo.getChild(RealParameter.class);
            if (parameter == null) {
                if (cxo.getChildCount() < 1) {
                    throw new XMLParseException("Distribution parameter, " + name + ", is missing a value or parameter element");
                }
                try {
                    double value = cxo.getDoubleChild(0);
                    parameter = new RealParameter(value+"");
                } catch (XMLParseException xpe) {
                    throw new XMLParseException("Distribution parameter, " + name + ", has bad value: " + xpe.getMessage());
                }
            }

            return parameter;
        } else {
            return null;
        }

    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        double offset = xo.getAttribute(OFFSET, 0.0);

        RealParameter shapeParameter = getParameterOrValue(SHAPE, xo);

        RealParameter parameter2;
        Gamma.mode parameterization;

        if (xo.hasChildNamed(SCALE)) {
            parameter2 = getParameterOrValue(SCALE, xo);
            parameterization = Gamma.mode.ShapeScale;
        } else if (xo.hasChildNamed(RATE)) {
            parameter2 = getParameterOrValue(RATE, xo);
            parameterization = Gamma.mode.ShapeRate;
        } else if (xo.hasChildNamed(MEAN)) {
            parameter2 = getParameterOrValue(MEAN, xo);
            parameterization = Gamma.mode.ShapeMean;
        } else {
            parameter2 = null;
            parameterization = Gamma.mode.OneParameter;
        }
        Gamma distr = new Gamma();
        distr.initByName("alpha", shapeParameter, "beta", parameter2, "mode", parameterization);
        return distr;

        
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(SHAPE,  new XMLSyntaxRule[]{new ElementRule(RealParameter.class, true)}, "Shape parameter"),
            new ElementRule(SCALE,  new XMLSyntaxRule[]{new ElementRule(RealParameter.class, true)}, "Scale parameter", true),
            new ElementRule(RATE,  new XMLSyntaxRule[]{new ElementRule(RealParameter.class, true)}, "Rate parameter", true),
            new ElementRule(MEAN,  new XMLSyntaxRule[]{new ElementRule(RealParameter.class, true)}, "Mean parameter", true),
            AttributeRule.newDoubleRule(OFFSET, true)
    };

    @Override
	public String getParserDescription() {
        return "The gamma probability distribution.";
    }

    @Override
	public Class getReturnType() {
        return Distribution.class;
    }
}
