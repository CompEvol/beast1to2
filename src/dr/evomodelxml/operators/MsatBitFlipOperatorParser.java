/*
 * MsatBitFlipOperatorParser.java
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

import dr.xml.*;
import beast.core.parameter.RealParameter;
import dr.inference.operators.MCMCOperator;

/**
 * @author Chieh-Hsi Wu
 *
 * Parser for MicrosatelliteAveragingOperatorParser
 */
public class MsatBitFlipOperatorParser extends AbstractXMLObjectParser{
    public static final String MODEL_CHOOSE = "modelChoose";
    public static final String DEPENDENCIES = "dependencies";
    public static final String VARIABLE_INDICES = "variableIndices";

    @Override
	public String getParserName() {
        return "msatModelSwitchOperator";
    }
         @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*
             double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);
        RealParameter modelChoose = (RealParameter) xo.getElementFirstChild(MODEL_CHOOSE);
        RealParameter dependencies = (RealParameter)xo.getElementFirstChild(DEPENDENCIES);
        int[] variableIndices;
            if(xo.hasChildNamed(VARIABLE_INDICES)){

                double[] temp = ((RealParameter)xo.getElementFirstChild(VARIABLE_INDICES)).getParameterValues();
                variableIndices = new int[temp.length];
                for(int i = 0; i < temp.length;i++){
                    variableIndices[i] = (int)temp[i];
                }

            }else{
                variableIndices = new int[]{0, 1, 2, 3, 4, 5};
            }

            return new MsatBitFlipOperator(modelChoose, dependencies, weight, variableIndices);
    */
		}
         //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************
         @Override
		public String getParserDescription() {
        return "This element returns a microsatellite averaging operator on a given parameter.";
    }
         @Override
		public Class getReturnType() {
        return MCMCOperator.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
            new ElementRule(MODEL_CHOOSE, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(DEPENDENCIES, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}),
            new ElementRule(VARIABLE_INDICES, new XMLSyntaxRule[]{new ElementRule(RealParameter.class)},true)
    };
}
