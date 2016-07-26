/*
 * RLTVLoggerParser.java
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

import beast.evolution.tree.Tree;
import dr.evomodel.tree.randomlocalmodel.RLTVLogger;
import dr.evomodel.tree.randomlocalmodel.RandomLocalTreeVariable;
import dr.xml.*;

/**
 * Parses an element from an DOM document into a SpeciationModel. Recognises YuleModel.
 */
public class RLTVLoggerParser extends AbstractXMLObjectParser {

    private static final String RANDOM_LOCAL_LOGGER = "randomLocalLogger";
    private static final String FILENAME = "fileName";
    private static final String LOG_EVERY = "logEvery";

        @Override
		public String getParserName() {
            return RANDOM_LOCAL_LOGGER;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

            Tree treeModel = (Tree) xo.getChild(Tree.class);
            RandomLocalTreeVariable randomLocal =
                    (RandomLocalTreeVariable) xo.getChild(RandomLocalTreeVariable.class);

            String fileName = xo.getStringAttribute(FILENAME);
            int logEvery = xo.getIntegerAttribute(LOG_EVERY);

            TabDelimitedFormatter formatter = null;
            try {
                formatter = new TabDelimitedFormatter(new PrintWriter(new FileWriter(fileName)));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new RLTVLogger(formatter, logEvery, treeModel, randomLocal);
        */
		}

        //************************************************************************
        // AbstractXMLObjectParser implementation
        //************************************************************************

        @Override
		public String getParserDescription() {
            return "A speciation model of a Yule process whose rate can evolve down the tree.";
        }

        @Override
		public Class getReturnType() {
            return RLTVLogger.class;
        }

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
                new ElementRule(Tree.class),
                new ElementRule(RandomLocalTreeVariable.class),
                AttributeRule.newIntegerRule(LOG_EVERY),
                AttributeRule.newStringRule(FILENAME),
        };
}
