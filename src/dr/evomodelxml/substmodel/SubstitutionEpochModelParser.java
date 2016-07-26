/*
 * SubstitutionEpochModelParser.java
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

package dr.evomodelxml.substmodel;

import dr.evomodel.substmodel.AbstractSubstitutionModel;
import dr.evomodel.substmodel.SubstitutionEpochModel;
import dr.inference.model.Parameter;
import dr.xml.*;

/**
 */
public class SubstitutionEpochModelParser extends AbstractXMLObjectParser {

    public static final String SUBSTITUTION_EPOCH_MODEL = "substitutionEpochModel";
    public static final String MODELS = "models";
    public static final String TRANSITION_TIMES = "transitionTimes";

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        DataType dataType = null;
        FrequencyModel freqModel = null;
        List<SubstitutionModel> modelList = new ArrayList<SubstitutionModel>();
        XMLObject cxo = xo.getChild(MODELS);
        for (int i = 0; i < cxo.getChildCount(); i++) {
            SubstitutionModel model = (SubstitutionModel) cxo.getChild(i);

            if (dataType == null) {
                dataType = model.getDataType();
            } else if (dataType != model.getDataType())
                throw new XMLParseException("Substitution models across epoches must use the same data type.");

            if (freqModel == null) {
                freqModel = model.getFrequencyModel();
            } else if (freqModel != model.getFrequencyModel())
                throw new XMLParseException("Substitution models across epoches must currently use the same frequency model.\nHarass Marc to fix this.");

            modelList.add(model);
        }

        Parameter transitionTimes = (Parameter) xo.getChild(Parameter.class);

        if (transitionTimes.getDimension() != modelList.size() - 1) {
            throw new XMLParseException("# of transition times must equal # of substitution models - 1\n" + transitionTimes.getDimension() + "\n" + modelList.size());
        }

        return new SubstitutionEpochModel(SUBSTITUTION_EPOCH_MODEL, modelList, transitionTimes, dataType, freqModel);
    */
		}

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return new XMLSyntaxRule[]{
                new ElementRule(MODELS,
                        new XMLSyntaxRule[]{
                                new ElementRule(AbstractSubstitutionModel.class, 1, Integer.MAX_VALUE),
                        }),
                new ElementRule(Parameter.class),
        };
    }

    @Override
	public String getParserDescription() {
        return null;
    }

    @Override
	public Class getReturnType() {
        return SubstitutionEpochModel.class;
    }

    @Override
	public String getParserName() {
        return SUBSTITUTION_EPOCH_MODEL;
    }
}
