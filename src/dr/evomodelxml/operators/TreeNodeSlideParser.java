/*
 * TreeNodeSlideParser.java
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

import beast.evolution.operators.NodeReheight;
import beast.evolution.tree.Tree;
import dr.evomodelxml.speciation.SpeciesBindingsParser;
import dr.inference.operators.MCMCOperator;
import dr.xml.*;

/**
 */
public class TreeNodeSlideParser extends AbstractXMLObjectParser {
    public static final String TREE_NODE_REHEIGHT = "nodeReHeight";

    @Override
	public String getParserName() {
        return TREE_NODE_REHEIGHT;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        SpeciesBindingsParser.Binding species = (SpeciesBindingsParser.Binding) xo.getChild(SpeciesBindingsParser.Binding.class);
        Tree tree = (Tree) xo.getChild(Tree.class);

        final double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);
        
        NodeReheight operator = new NodeReheight();
        operator.initByName("weight", weight, "tree", tree, "taxonset", species.taxonset);
        return operator;
//            final double range = xo.getAttribute("range", 1.0);
//            if( range <= 0 || range > 1.0 ) {
//                throw new XMLParseException("range out of range");
//            }
        //final boolean oo = xo.getAttribute("outgroup", false);
//        return new TreeNodeSlide(tree, species /*, range* //*, oo* /, weight);
		}

    @Override
	public String getParserDescription() {
        return "Specialized Species tree operator, transform tree without breaking embedding of gene trees.";
    }

    @Override
	public Class getReturnType() {
        return NodeReheight.class;
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return new XMLSyntaxRule[]{
                AttributeRule.newDoubleRule(MCMCOperator.WEIGHT),
               // AttributeRule.newDoubleRule("range", true),
              //  AttributeRule.newBooleanRule("outgroup", true),

                new ElementRule(SpeciesBindingsParser.Binding.class),
                new ElementRule(Tree.class)
        };
    }

}
