/*
 * UPGMATreeParser.java
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

package dr.evoxml;

import dr.evomodelxml.tree.TreeModelParser;
import dr.xml.*;

import java.util.logging.Logger;

import beast.evolution.alignment.Alignment;
import beast.evolution.alignment.distance.Distance;
import beast.evolution.tree.Node;
import beast.util.ClusterTree;
import beast.util.Randomizer;

/**
 * @author Alexei Drummond
 * @author Andrew Rambaut
 * @author Marc A. Suchard
 * @version $Id: UPGMATreeParser.java,v 1.6 2006/07/28 11:27:32 rambaut Exp $
 */
public class UPGMATreeParser extends AbstractXMLObjectParser {

    //
    // Public stuff
    //

    public static final String UPGMA_TREE = "upgmaTree";
    //public static final String DISTANCES = "distances";
    public static final String ROOT_HEIGHT = TreeModelParser.ROOT_HEIGHT;
    public static final String RANDOMIZE = "nonzeroBranchLengths";

    @Override
	public String getParserName() {
        return UPGMA_TREE;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        boolean usingDatesSpecified = false;
        boolean usingDates = true;
        double rootHeight = xo.getAttribute(ROOT_HEIGHT, -1.0);

        if (xo.hasAttribute(SimpleTreeParser.USING_DATES)) {
            usingDatesSpecified = true;
            usingDates = xo.getBooleanAttribute(SimpleTreeParser.USING_DATES);
        }

        Distance distances = (Distance) xo.getChild(Distance.class);

    	Alignment alignment = null;
    	for (int i = 0; i < xo.getChildCount(); i++) {
        	Object o = xo.getRawChild(i); 
        	if (o instanceof XMLObject) {
        		XMLObject xco = (XMLObject) o;
        		if (xco.getName().equals("distanceMatrix")) {
        			alignment = (Alignment)xco.getChild(Alignment.class);
        		}
        	}
        }
        
        ClusterTree tree = new ClusterTree();
        tree.initByName("distance", distances, "clusterType", "upgma", "taxa", alignment);
        return tree;

        /** TODO: add more functionality: 
        if (rootHeight > 0) {
            double scaleFactor = rootHeight / tree.getNodeHeight(tree.getRoot());

            for (int i = 0; i < tree.getInternalNodeCount(); i++) {
                Node node = tree.getNode(i);
                double height = node.getHeight();
                node.setHeight(height * scaleFactor);
            }
        }

        if (usingDates) {

            dr.evolution.util.Date mostRecent = null;
            for (int i = 0; i < tree.getTaxonCount(); i++) {

                dr.evolution.util.Date date = (dr.evolution.util.Date) tree.getTaxonAttribute(i, dr.evolution.util.Date.DATE);

                if (date == null) {
                    date = (dr.evolution.util.Date) tree.getNodeAttribute(tree.getExternalNode(i), dr.evolution.util.Date.DATE);
                }

                if (date != null && ((mostRecent == null) || date.after(mostRecent))) {
                    mostRecent = date;
                }
            }

            for (int i = 0; i < tree.getInternalNodeCount(); i++) {
                dr.evolution.util.Date date = (dr.evolution.util.Date) tree.getNodeAttribute(tree.getInternalNode(i), dr.evolution.util.Date.DATE);

                if (date != null && ((mostRecent == null) || date.after(mostRecent))) {
                    mostRecent = date;
                }
            }

            if (mostRecent == null) {
                if (usingDatesSpecified) {
                    throw new XMLParseException("no date elements in tree (and usingDates attribute set)");
                }
            } else {
                TimeScale timeScale = new TimeScale(mostRecent.getUnits(), true, mostRecent.getAbsoluteTimeValue());

                for (int i = 0; i < tree.getTaxonCount(); i++) {
                    dr.evolution.util.Date date = (dr.evolution.util.Date) tree.getTaxonAttribute(i, dr.evolution.util.Date.DATE);

                    if (date == null) {
                        date = (dr.evolution.util.Date) tree.getNodeAttribute(tree.getExternalNode(i), dr.evolution.util.Date.DATE);
                    }

                    if (date != null) {
                        double height = timeScale.convertTime(date.getTimeValue(), date);
                        tree.setNodeHeight(tree.getExternalNode(i), height);
                    }
                }

                for (int i = 0; i < tree.getInternalNodeCount(); i++) {
                    dr.evolution.util.Date date = (dr.evolution.util.Date) tree.getNodeAttribute(tree.getInternalNode(i), dr.evolution.util.Date.DATE);

                    if (date != null) {
                        double height = timeScale.convertTime(date.getTimeValue(), date);
                        tree.setNodeHeight(tree.getInternalNode(i), height);
                    }
                }

                MutableTree.Utils.correctHeightsForTips(tree);
            }

        }

        if (rootHeight > 0) {
            double scaleFactor = rootHeight / tree.getNodeHeight(tree.getRoot());

            for (int i = 0; i < tree.getInternalNodeCount(); i++) {
                NodeRef node = tree.getInternalNode(i);
                double height = tree.getNodeHeight(node);
                tree.setNodeHeight(node, height * scaleFactor);
            }
        }

        if (xo.getAttribute(RANDOMIZE, false)) {
            shakeTree(tree);
        }


        return tree;
    */
		}

    private boolean shakeNode(ClusterTree tree, Node node) {
        if (node.isRoot() || node.isLeaf()) {
            return false;
        }

        boolean shake = false;
        if (node.getLength() <= tolerance) {
            shake = true;
        }
        double maxHeight = node.getParent().getHeight();
        double minHeight = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < node.getChildCount(); i++) {
            Node child = node.getChild(i);
            if (child.getLength() <= tolerance) {
                shake = true;
            }
            double thisHeight = child.getHeight();
            if (thisHeight > minHeight) {
                minHeight = thisHeight;
            }
        }
        if (shake) {
            double draw = minHeight + (maxHeight - minHeight) * Randomizer.nextDouble();
            node.setHeight(draw);
        }
        return shake;
    }

    private void shakeTree(ClusterTree tree) {
        boolean shake = true;
        int[] permutation = new int[tree.getNodeCount()];
        for (int i = 0; i < tree.getNodeCount(); i++) {
            permutation[i] = i;
        }
        while (shake) {
            Logger.getLogger("dr.evomodelxml").info("Adjusting heights in UPGMA tree");
            Randomizer.permute(permutation);
            shake = false;
            for (int i = 0; i < tree.getNodeCount(); i++) {
                Node node = tree.getNode(permutation[i]);
                if (shakeNode(tree, node)) {
                    shake = true;
                }
            }
        }
    }

    private static double tolerance = 0.0;

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newBooleanRule(SimpleTreeParser.USING_DATES, true),
            AttributeRule.newDoubleRule(ROOT_HEIGHT, true),
            AttributeRule.newBooleanRule(RANDOMIZE, true),
            new ElementRule(Distance.class)
    };

    @Override
	public String getParserDescription() {
        return "This element returns a UPGMA tree generated from the given distances.";
    }

    @Override
	public Class getReturnType() {
        return ClusterTree.class;
    }
}
