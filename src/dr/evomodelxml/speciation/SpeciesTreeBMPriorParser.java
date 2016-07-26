/*
 * SpeciesTreeBMPriorParser.java
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

package dr.evomodelxml.speciation;

import dr.evomodel.speciation.SpeciesTreeBMPrior;
import dr.evomodel.speciation.SpeciesTreeModel;
import dr.inference.distribution.ParametricDistributionModel;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 */
public class SpeciesTreeBMPriorParser extends AbstractXMLObjectParser {
    public static final String TIPS = "tipsDistribution";

    public static final String STPRIOR = "STPopulationPrior";

    public static final String LOG_ROOT = "log_root";
    public static final String STSIGMA = "STsigma";
    public static final String SIGMA = "sigma";

       @Override
	public String getParserDescription() {
            return "";
        }

        @Override
		public Class getReturnType() {
            return SpeciesTreeBMPrior.class;
        }

        @Override
		public String getParserName() {
            return STPRIOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*
            final SpeciesTreeModel st = (SpeciesTreeModel) xo.getChild(SpeciesTreeModel.class);

            //ParametricDistributionModel pr = (ParametricDistributionModel) xo.getChild(ParametricDistributionModel.class);
            final Object child = xo.getChild(SIGMA);
            RealParameter popSigma = child != null ? (RealParameter)((XMLObject) child).getChild(RealParameter.class) : null;
            RealParameter stSigma = (RealParameter)((XMLObject)xo.getChild(STSIGMA)).getChild(RealParameter.class);

            final XMLObject cxo = (XMLObject) xo.getChild(TIPS);
            final ParametricDistributionModel tipsPrior =
                    (ParametricDistributionModel) cxo.getChild(ParametricDistributionModel.class);
            final boolean logRoot = xo.getAttribute(LOG_ROOT, false);
            return new SpeciesTreeBMPrior(st, popSigma, stSigma, tipsPrior, logRoot);
        */
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return new XMLSyntaxRule[]{
                    AttributeRule.newBooleanRule(LOG_ROOT, true),
                    new ElementRule(SpeciesTreeModel.class),
                    new ElementRule(TIPS,
                            new XMLSyntaxRule[] { new ElementRule(ParametricDistributionModel.class) }),
                    //new ElementRule(ParametricDistributionModel.class),
                    new ElementRule(SIGMA, new XMLSyntaxRule[] { new ElementRule(RealParameter.class) }, true),
                    new ElementRule(STSIGMA, new XMLSyntaxRule[] { new ElementRule(RealParameter.class) })
            };
        }
}
