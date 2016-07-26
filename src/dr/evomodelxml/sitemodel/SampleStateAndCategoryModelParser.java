/*
 * SampleStateAndCategoryModelParser.java
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

package dr.evomodelxml.sitemodel;

import dr.evomodel.sitemodel.SampleStateAndCategoryModel;
import dr.evomodel.substmodel.SubstitutionModel;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 *
 */
public class SampleStateAndCategoryModelParser extends AbstractXMLObjectParser {

    public static final String SAMPLE_STATE_AND_CATEGORY_MODEL = "sampleStateAndCategoryModel";
    public static final String MUTATION_RATE = "mutationRate";
    public static final String CATEGORY_PARAMETER = "categoriesParameter";

    @Override
	public String getParserName() {
        return SAMPLE_STATE_AND_CATEGORY_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        XMLObject cxo = xo.getChild(MUTATION_RATE);
        RealParameter muParam = (RealParameter) cxo.getChild(RealParameter.class);

        cxo = xo.getChild(CATEGORY_PARAMETER);
        RealParameter catParam = (RealParameter) cxo.getChild(RealParameter.class);

        Vector subModels = new Vector();
        for (int i = 0; i < xo.getChildCount(); i++) {

            if (xo.getChild(i) instanceof SubstitutionModel) {
                subModels.addElement(xo.getChild(i));
            }

        }

        return new SampleStateAndCategoryModel(muParam, catParam, subModels);

    */
		}
    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A SiteModel that has a discrete distribution of substitution models over sites, " +
                "designed for sampling of rate categories and internal states.";
    }

    @Override
	public Class getReturnType() {
        return SampleStateAndCategoryModel.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(MUTATION_RATE,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(CATEGORY_PARAMETER,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(SubstitutionModel.class, 1, Integer.MAX_VALUE)
    };

}
