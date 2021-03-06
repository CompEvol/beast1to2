/*
 * BifractionalDiffusionModelParser.java
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

package dr.evoxml;

import dr.xml.*;
import beast.core.parameter.RealParameter;
import dr.evomodel.continuous.MultivariateDiffusionModel;

/**
 * @author Marc Suchard
 */

public class BifractionalDiffusionModelParser extends AbstractXMLObjectParser {

    public static final String BIFRACTIONAL_DIFFUSION_PROCESS = "bifractionalDiffusionModel";
    public static final String ALPHA_PARAMETER = "alpha";
    public static final String BETA_PARAMETER = "beta";

    @Override
	public String getParserName() {
            return BIFRACTIONAL_DIFFUSION_PROCESS;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

            XMLObject cxo = xo.getChild(ALPHA_PARAMETER);
            RealParameter alpha = (RealParameter) cxo.getChild(RealParameter.class);

            cxo = xo.getChild(BETA_PARAMETER);
            RealParameter beta = (RealParameter) cxo.getChild(RealParameter.class);

            return new BifractionalDiffusionModel(alpha, beta);
        */
		}

        //************************************************************************
        // AbstractXMLObjectParser implementation
        //************************************************************************

        @Override
		public String getParserDescription() {
            return "Describes a bivariate diffusion process using a bifractional random walk";
        }

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                new ElementRule(ALPHA_PARAMETER,
                        new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
                new ElementRule(BETA_PARAMETER,
                        new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
        };

        @Override
		public Class getReturnType() {
            return MultivariateDiffusionModel.class;
        }
}
