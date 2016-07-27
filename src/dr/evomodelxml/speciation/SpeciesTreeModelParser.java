/*
 * SpeciesTreeModelParser.java
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

import beast.core.parameter.RealParameter;
import beast.evolution.speciation.SpeciesTreePrior;
import beast.evolution.tree.Tree;
import beast1to2.Beast1to2Converter;
import dr.util.Attributable;
import dr.xml.*;

/**
 */
public class SpeciesTreeModelParser extends AbstractXMLObjectParser {
    public static final String SPECIES_TREE = "speciesTree";

    public static final String SPP_SPLIT_POPULATIONS = "sppSplitPopulations";
    public static final String COALESCENT_POINTS_POPULATIONS = "coalescentPointsPopulations";
    public static final String COALESCENT_POINTS_INDICATORS = "coalescentPointsIndicators";

    public static final String BMPRIOR = "bmPrior";
    public static final String CONST_ROOT_POPULATION = "constantRoot";
    public static final String CONSTANT_POPULATION = "constantPopulation";
    

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        SpeciesBindingsParser.Binding spb = (SpeciesBindingsParser.Binding) xo.getChild(SpeciesBindingsParser.Binding.class);

        RealParameter coalPointsPops = null;
        RealParameter coalPointsIndicators = null;
        final Boolean cr = xo.getAttribute(CONST_ROOT_POPULATION, false);
        final Boolean cp = xo.getAttribute(CONSTANT_POPULATION, false);

        final Boolean bmp = xo.getAttribute(BMPRIOR, false);
        {
            XMLObject cxo = xo.getChild(COALESCENT_POINTS_POPULATIONS);
            if( cxo != null ) {
            	throw new XMLParseException(Beast1to2Converter.NIY + " " + COALESCENT_POINTS_POPULATIONS + " attribute");
//                final double value = cxo.getAttribute(Attributable.VALUE, 1.0);
//                coalPointsPops = SpeciesTreeModel.createCoalPointsPopParameter(spb, cxo.getAttribute(Attributable.VALUE, value), bmp);
//                ParameterParser.replaceParameter(cxo, coalPointsPops);
//                coalPointsPops.addBounds(
//                        new RealParameter.DefaultBounds(Double.MAX_VALUE, 0, coalPointsPops.getDimension()));
//
//                cxo = xo.getChild(COALESCENT_POINTS_INDICATORS);
//                if( cxo == null ) {
//                    throw new XMLParseException("Must have indicators");
//                }
//                coalPointsIndicators = new RealParameter.Default(coalPointsPops.getDimension(), 0);
//                ParameterParser.replaceParameter(cxo, coalPointsIndicators);
            } else {
               // assert ! bmp;
            }
        }

        final XMLObject cxo = xo.getChild(SPP_SPLIT_POPULATIONS);

        final double value = cxo.getAttribute(Attributable.VALUE, 1.0);
        final boolean nonConstRootPopulation = coalPointsPops == null && !cr;
        final RealParameter sppSplitPopulations = (RealParameter) cxo.getChild(RealParameter.class);
        //		SpeciesTreeModel.createSplitPopulationsParameter(spb, value, nonConstRootPopulation, cp);
        //ParameterParser.replaceParameter(cxo, sppSplitPopulations);

        //final RealParameter.DefaultBounds bounds =
        //        new RealParameter.DefaultBounds(Double.MAX_VALUE, 0, sppSplitPopulations.getDimension());
        sppSplitPopulations.setBounds(0.0, Double.MAX_VALUE);

       final Tree startTree = (Tree) xo.getChild(Tree.class);
        
        
        Tree tree = new Tree();
        tree.setID(xo.getId());
        spb.taxonset.initAndValidate();
        tree.initByName("taxonset", spb.taxonset);
        
        spb.sptree = tree;
        spb.popFunction = (cp ? "constant" : (cr ? "linear_with_constant_root" : "linear"));
        spb.sppSplitPopulations = sppSplitPopulations;
        return tree;
//        return new SpeciesTreeModel(spb, sppSplitPopulations, coalPointsPops, coalPointsIndicators, startTree, bmp,
//                nonConstRootPopulation, cp);
		}

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return new XMLSyntaxRule[]{
                AttributeRule.newBooleanRule(BMPRIOR, true),
                AttributeRule.newBooleanRule(CONST_ROOT_POPULATION, true),
                 AttributeRule.newBooleanRule(CONSTANT_POPULATION, true),
                new ElementRule(SpeciesBindingsParser.Binding.class),
                // A starting tree. Can be very minimal, i.e. no branch lengths and not resolved
                new ElementRule(Tree.class, true),
                new ElementRule(SPP_SPLIT_POPULATIONS, new XMLSyntaxRule[]{
                        AttributeRule.newDoubleRule(Attributable.VALUE, true),
                        new ElementRule(RealParameter.class)}),

                new ElementRule(COALESCENT_POINTS_POPULATIONS, new XMLSyntaxRule[]{
                        AttributeRule.newDoubleRule(Attributable.VALUE, true),
                        new ElementRule(RealParameter.class)}, true),

                new ElementRule(COALESCENT_POINTS_INDICATORS, new XMLSyntaxRule[]{
                        new ElementRule(RealParameter.class)}, true),
        };
    }

    @Override
	public String getParserDescription() {
        return "Species tree which includes demographic function per branch.";
    }

    @Override
	public Class getReturnType() {
        return Tree.class;
    }

    @Override
	public String getParserName() {
        return SPECIES_TREE;
    }


}
