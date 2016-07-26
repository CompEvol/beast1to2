/*
 * MarkovJumpsTreeLikelihoodParser.java
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

package dr.evomodelxml.treelikelihood;

import dr.evomodel.treelikelihood.AncestralStateTreeLikelihood;
import dr.xml.*;

/**
 */
public class MarkovJumpsTreeLikelihoodParser extends AbstractXMLObjectParser {

    public static final String RECONSTRUCTING_TREE_LIKELIHOOD = "markovJumpsTreeLikelihood";

    @Override
	public String getParserName() {
        return RECONSTRUCTING_TREE_LIKELIHOOD;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*
        throw new XMLParseException("MarkovJump functionality is only support when using the BEAGLE library.\nAvailable from http://github.com/beagle-dev/beagle-lib/");
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "This element represents the likelihood of a patternlist on a tree given the site model.";
    }

    @Override
	public Class getReturnType() {
        return AncestralStateTreeLikelihood.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return null;
    }
}