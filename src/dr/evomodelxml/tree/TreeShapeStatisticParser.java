/*
 * TreeShapeStatisticParser.java
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

import beast.evolution.tree.Tree;
import dr.evomodel.tree.TreeShapeStatistic;
import dr.inference.model.Statistic;
import dr.xml.*;

/**
 */
public class TreeShapeStatisticParser extends AbstractXMLObjectParser {

    public static final String TREE_SHAPE_STATISTIC = "treeShapeStatistics";
    public static final String TARGET = "target";

    @Override
	public String getParserName() {
        return TREE_SHAPE_STATISTIC;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        String name = xo.getAttribute(Statistic.NAME, xo.getId());
        Tree target = (Tree) xo.getElementFirstChild(TARGET);

        return new TreeShapeStatistic(name, target);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A statistic that reports a handful of tree shape statistics on the given target tree.";
    }

    @Override
	public Class getReturnType() {
        return TreeShapeStatistic.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
            new StringAttributeRule(Statistic.NAME, "A name for this statistic primarily for the purposes of logging", true),
            new ElementRule(TARGET,
                    new XMLSyntaxRule[]{new ElementRule(Tree.class)})
    };

}
