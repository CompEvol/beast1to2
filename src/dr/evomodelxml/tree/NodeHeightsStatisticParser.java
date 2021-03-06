/*
 * NodeHeightsStatisticParser.java
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

import dr.evomodel.tree.NodeHeightsStatistic;
import beast.evolution.tree.Tree;
import beast.core.parameter.RealParameter;
import dr.inference.model.Statistic;
import dr.xml.*;

/**
 */
public class NodeHeightsStatisticParser extends AbstractXMLObjectParser {

    public static final String NODE_HEIGHTS_STATISTIC = "nodeHeightsStatistic";

    @Override
	public String getParserName() {
        return NODE_HEIGHTS_STATISTIC;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

        String name = xo.getAttribute(Statistic.NAME, xo.getId());
        Tree tree = (Tree) xo.getChild(Tree.class);

        RealParameter groupSizes = (RealParameter) xo.getChild(RealParameter.class);

        return new NodeHeightsStatistic(name, tree, groupSizes);
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public String getParserDescription() {
        return "A statistic that returns the heights of each internal node in increasing order (or groups them by a group size parameter)";
    }

    @Override
	public Class getReturnType() {
        return NodeHeightsStatistic.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newStringRule(Statistic.NAME, true),
            new ElementRule(Tree.class),
            new ElementRule(RealParameter.class, true),
    };

}
