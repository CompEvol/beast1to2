/*
 * EmpiricalTreeDistributionModelParser.java
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

package dr.evomodelxml;

import dr.xml.*;
import beast.evolution.alignment.TaxonSet;
import dr.evomodel.tree.EmpiricalTreeDistributionModel;

/**
 * @author Andrew Rambaut
 *         <p/>
 *         Reads a list of trees from a NEXUS file.
 */
public class EmpiricalTreeDistributionModelParser extends AbstractXMLObjectParser {

    public static final String RATE_ATTRIBUTE_NAME = "rateAttribute";
    public static final String STARTING_TREE = "startingTree";

    @Override
	public String getParserName() {
        return EmpiricalTreeDistributionModel.EMPIRICAL_TREE_DISTRIBUTION_MODEL;
    }

    @Override
	public String getParserDescription() {
        return "Read a list of trees from a NEXUS file.";
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*
        final String fileName = xo.getStringAttribute(FILE_NAME);
//        int burnin = 0;
//        if (xo.hasAttribute(BURNIN)) {
//            burnin = xo.getIntegerAttribute(BURNIN);
//        }

        Logger.getLogger("dr.evomodel").info("Creating the empirical tree distribution model, '" + xo.getId() + "'");

        TaxonSet taxa = (TaxonSet)xo.getChild(TaxonSet.class);

        final File file = FileHelpers.getFile(fileName);

        Tree[] trees = null;
        try {
            FileReader reader = new FileReader(file);
            NexusImporter importer = new NexusImporter(reader);
            trees = importer.importTrees(taxa, true); // Re-order taxon numbers to original TaxonSet order

        } catch (FileNotFoundException e) {
            throw new XMLParseException(e.getMessage());
        } catch (IOException e) {
            throw new XMLParseException(e.getMessage());
        } catch (Importer.ImportException e) {
            throw new XMLParseException(e.getMessage());
        }
        
        Logger.getLogger("dr.evomodel").info("    Read " + trees.length + " trees from file, " + fileName);

        int startingTree = xo.getAttribute(STARTING_TREE, -1); // default is random tree

        return new EmpiricalTreeDistributionModel(trees, startingTree);
    */
		}

    public static final String FILE_NAME = "fileName";
//    public static final String BURNIN = "burnin";

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return new XMLSyntaxRule[]{
                AttributeRule.newIntegerRule(STARTING_TREE, true),
                new StringAttributeRule(FILE_NAME,
                        "The name of a NEXUS tree file"),
//                AttributeRule.newIntegerRule(BURNIN, true,
//                        "The number of trees to exclude"),
                new ElementRule(TaxonSet.class),
        };
    }


    @Override
	public Class getReturnType() {
        return EmpiricalTreeDistributionModel.class;
    }
}