/*
 * DummyLikelihoodParser.java
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

import dr.inference.model.DummyLikelihood;
import dr.inference.model.Model;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * Reads a distribution likelihood from a DOM Document element.
 */
public class DummyLikelihoodParser extends AbstractXMLObjectParser {

    public static final String DUMMY_LIKELIHOOD = "dummyLikelihood";

    @Override
	public String getParserName() { return DUMMY_LIKELIHOOD; }

    @Override
	public Object parseXMLObject(XMLObject xo) {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        Model model = (Model)xo.getChild(Model.class);
        RealParameter parameter = (RealParameter)xo.getChild(RealParameter.class);

        if (model == null) {
            model = new DefaultModel();
        }
        final DummyLikelihood likelihood = new DummyLikelihood(model);

        ((DefaultModel)model).addVariable(parameter);

        return likelihood;
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A function wraps a component model that would otherwise not be registered with the MCMC. Always returns a log likelihood of zero.";
    }

    @Override
	public Class getReturnType() { return DummyLikelihood.class; }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() { return rules; }

    private final XMLSyntaxRule[] rules = {
            new XORRule(
                    new ElementRule(Model.class, "A model element"),
                    new ElementRule(RealParameter.class, "A parameter")
            )
    };
}
