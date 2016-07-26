/*
 * MicrosatelliteSamplerTreeModelParser.java
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

package dr.evomodelxml.tree;

import dr.evolution.alignment.Patterns;
import dr.evomodel.tree.MicrosatelliteSamplerTreeModel;
import beast.evolution.tree.Tree;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * @author Chieh-Hsi Wu
 *
 * Parser for MicrsatelliteSamplerTreeModel
 *
 */
public class MicrosatelliteSamplerTreeModelParser extends AbstractXMLObjectParser {
    public static final String TREE_MICROSATELLITE_SAMPLER_MODEL = "microsatelliteSamplerTreeModel";
    public static final String TREE = "tree";
    public static final String INTERNAL_VALUES = "internalValues";
    public static final String EXTERNAL_VALUES = "externalValues";
    public static final String USE_PROVIDED_INTERNAL_VALUES = "provideInternalNodeValues";

    @Override
	public String getParserName(){
        return TREE_MICROSATELLITE_SAMPLER_MODEL;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*
        Tree  tree = (Tree)xo.getElementFirstChild(TREE);
        RealParameter internalVal = (RealParameter)xo.getElementFirstChild(INTERNAL_VALUES);
        Patterns microsatellitePattern = (Patterns)xo.getElementFirstChild(EXTERNAL_VALUES);
        int[] externalValues = microsatellitePattern.getPattern(0);
        HashMap<String, Integer> taxaMap = new HashMap<String, Integer>(externalValues.length);
        boolean internalValuesProvided = false;
        if(xo.hasAttribute(USE_PROVIDED_INTERNAL_VALUES)){
            internalValuesProvided = xo.getBooleanAttribute(USE_PROVIDED_INTERNAL_VALUES);
        }
        for(int i = 0; i < externalValues.length; i++){
            taxaMap.put(microsatellitePattern.getTaxonId(i),i);
        }

        String modelId = xo.getAttribute("id", "treeMicrosatelliteSamplerModel");
        return new MicrosatelliteSamplerTreeModel(modelId, tree, internalVal, microsatellitePattern, externalValues, taxaMap, internalValuesProvided);
    */
		}

    @Override
	public XMLSyntaxRule[] getSyntaxRules() { return rules; }
    private final XMLSyntaxRule[] rules = {
            AttributeRule.newBooleanRule(USE_PROVIDED_INTERNAL_VALUES, true),
            new ElementRule(TREE, Tree.class),
            new ElementRule(INTERNAL_VALUES, RealParameter.class),
            new ElementRule(EXTERNAL_VALUES, Patterns.class)
    };

    @Override
	public String getParserDescription(){
        return "This parser returns a TreeMicrosatelliteSamplerModel Object";
    }

    @Override
	public Class getReturnType(){
        return MicrosatelliteSamplerTreeModel.class;

    }
}
