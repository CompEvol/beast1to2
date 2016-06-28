/*
 * CTMCScalePriorParser.java
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

package dr.evomodelxml.MSSD;

import dr.app.beagle.evomodel.substmodel.SubstitutionModel;
import dr.evomodel.MSSD.CTMCScalePrior;

import dr.xml.*;

import java.util.logging.Logger;

import beast.core.parameter.Parameter;
import beast.evolution.tree.Tree;
import beast.math.distributions.Prior;
import beast.math.distributions.Uniform;

/**
 *
 */
public class CTMCScalePriorParser extends AbstractXMLObjectParser {
    public static final String MODEL_NAME = "ctmcScalePrior";
    public static final String SCALEPARAMETER = "ctmcScale";
    public static final String RECIPROCAL = "reciprocal";
    public static final String TRIAL = "trial";

    public String getParserName() {
        return MODEL_NAME;
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		
		// TODO: this is not a proper implementation
		// it just produces a dummy uniform prior that has no effect on the posterior
        Parameter<?> ctmcScale = (Parameter<?>) xo.getElementFirstChild(SCALEPARAMETER);
		Prior prior = new Prior();
		Uniform distr = new Uniform();
		distr.initByName("lower", Double.NEGATIVE_INFINITY, "upper", Double.POSITIVE_INFINITY);
		prior.initByName("distr", distr, "x", ctmcScale);
		return prior;
		/*

        TreeModel treeModel = (TreeModel) xo.getChild(TreeModel.class);
        Parameter ctmcScale = (Parameter) xo.getElementFirstChild(SCALEPARAMETER);
        boolean reciprocal = xo.getAttribute(RECIPROCAL, false);
        boolean trial = xo.getAttribute(TRIAL, false);
        SubstitutionModel substitutionModel = (SubstitutionModel) xo.getChild(SubstitutionModel.class);

        Logger.getLogger("dr.evolution").info("\n ---------------------------------\nCreating ctmcScalePrior model.");
        Logger.getLogger("dr.evolution").info("\tIf you publish results using this prior, please reference:");
        Logger.getLogger("dr.evolution").info("\t\t 1. Ferreira and Suchard (2008) for the conditional reference prior on CTMC scale parameter prior;");

        return new CTMCScalePrior(MODEL_NAME, ctmcScale, treeModel, reciprocal, substitutionModel, trial);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public String getParserDescription() {
        return "This element represents the prior for CTMC scale parameter.";
    }

    public Class getReturnType() {
        return Prior.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(Tree.class),
            new ElementRule(SCALEPARAMETER, new XMLSyntaxRule[]{new ElementRule(Parameter.class)}),
            AttributeRule.newBooleanRule(RECIPROCAL, true),
            new ElementRule(SubstitutionModel.class, true),
            AttributeRule.newBooleanRule(TRIAL, true),
    };
}
