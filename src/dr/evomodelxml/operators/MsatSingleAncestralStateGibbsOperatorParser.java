/*
 * MsatSingleAncestralStateGibbsOperatorParser.java
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

package dr.evomodelxml.operators;

import dr.inference.operators.MCMCOperator;
import dr.evomodel.operators.MsatSingleAncestralStateGibbsOperator;
import beast.core.parameter.RealParameter;
import dr.xml.*;
import dr.evomodel.tree.MicrosatelliteSamplerTreeModel;
import dr.evomodel.substmodel.MicrosatelliteModel;
import dr.evomodel.branchratemodel.BranchRateModel;

/**
 * @author Chieh-Hsi Wu
 */
public class MsatSingleAncestralStateGibbsOperatorParser extends AbstractXMLObjectParser {
    @Override
	public String getParserName(){
        return MsatSingleAncestralStateGibbsOperator.MSAT_SINGLE_ANCESTAL_STATE_GIBBS_OPERATOR;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*
        final double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);
        final RealParameter parameter = (RealParameter)xo.getChild(RealParameter.class);
        final MicrosatelliteSamplerTreeModel msatSamplerTreeModel = (MicrosatelliteSamplerTreeModel)xo.getChild(MicrosatelliteSamplerTreeModel.class);
        final MicrosatelliteModel msatModel = (MicrosatelliteModel)xo.getChild(MicrosatelliteModel.class);
        final BranchRateModel branchRateModel = (BranchRateModel)xo.getChild(BranchRateModel.class);

        return new MsatSingleAncestralStateGibbsOperator(parameter, msatSamplerTreeModel, msatModel, branchRateModel,weight);
    */
		}

    @Override
	public String getParserDescription() {
        return "This element represents an operator that samples the state of a single ancestor given a microsatellite pattern and a tree";
    }

    @Override
	public Class getReturnType(){
        return MsatSingleAncestralStateGibbsOperator.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }
    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
            new ElementRule(RealParameter.class),
            new ElementRule(MicrosatelliteSamplerTreeModel.class),
            new ElementRule(MicrosatelliteModel.class),
            new ElementRule(BranchRateModel.class)
    };
}
