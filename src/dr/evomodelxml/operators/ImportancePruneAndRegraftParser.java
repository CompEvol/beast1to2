/*
 * ImportancePruneAndRegraftParser.java
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

import dr.evomodel.operators.ImportancePruneAndRegraft;
import dr.evomodel.tree.TreeModel;
import dr.inference.operators.MCMCOperator;
import dr.xml.*;

/**
 */
public class ImportancePruneAndRegraftParser extends AbstractXMLObjectParser {

    public static final String IMPORTANCE_PRUNE_AND_REGRAFT = "ImportancePruneAndRegraft";

    public String getParserName() {
        return IMPORTANCE_PRUNE_AND_REGRAFT;
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        TreeModel treeModel = (TreeModel) xo.getChild(TreeModel.class);
        double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);
        int samples = xo.getIntegerAttribute("samples");

        return new ImportancePruneAndRegraft(treeModel, weight, samples);
    */
		}

    // ************************************************************************
    // AbstractXMLObjectParser implementation
    // ************************************************************************

    public String getParserDescription() {
        return "This element represents a importance guided prune and regraft operator. "
                + "This operator prunes a random subtree and regrafts it below a node chosen by an importance distribution.";
    }

    public Class getReturnType() {
        return ImportancePruneAndRegraft.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
            AttributeRule.newIntegerRule("samples"),
            new ElementRule(TreeModel.class)
    };

}
