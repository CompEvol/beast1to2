/*
 * TaxaParser.java
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

import beast.evolution.alignment.Taxon;
import beast.evolution.alignment.TaxonSet;
import dr.xml.*;

/**
 * @author Alexei Drummond
 * @author Andrew Rambaut
 *
 * @version $Id: TaxaParser.java,v 1.2 2005/05/24 20:25:59 rambaut Exp $
 */
public class TaxaParser extends AbstractXMLObjectParser {

    public static final String TAXA = "taxa";

    @Override
	public String getParserName() { return TAXA; }

    @Override
	public String getExample() {
        return "<!-- A list of six taxa -->\n"+
                "<taxa id=\"greatApes\">\n"+
                "	<taxon id=\"human\"/>\n"+
                "	<taxon id=\"chimp\"/>\n"+
                "	<taxon id=\"bonobo\"/>\n"+
                "	<taxon id=\"gorilla\"/>\n"+
                "	<taxon id=\"orangutan\"/>\n"+
                "	<taxon id=\"siamang\"/>\n"+
                "</taxa>\n" +
                "\n" +
                "<!-- A list of three taxa by references to above taxon objects -->\n"+
                "<taxa id=\"humanAndChimps\">\n"+
                "	<taxon idref=\"human\"/>\n"+
                "	<taxon idref=\"chimp\"/>\n"+
                "	<taxon idref=\"bonobo\"/>\n"+
                "</taxa>\n";
    }

    /** @return an instance of Node created from a DOM element */
    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        TaxonSet taxonList = new TaxonSet();

        for (int i = 0; i < xo.getChildCount(); i++) {
            Object child = xo.getChild(i);
            if (child instanceof Taxon) {
            	Taxon taxon = (Taxon)child;
                taxonList.taxonsetInput.setValue(taxon, taxonList);
            } else if (child instanceof TaxonSet) {
            	TaxonSet taxonList1 = (TaxonSet)child;
                for (Taxon taxon : taxonList1.getTaxonSet()) {
                    taxonList.taxonsetInput.setValue(taxon, taxonList);
                }
            } else {
                throwUnrecognizedElement(xo);
            }
        }
        taxonList.initAndValidate();
        return taxonList;
	}

    @Override
	public XMLSyntaxRule[] getSyntaxRules() { return rules; }

    private final XMLSyntaxRule[] rules = {
        new OrRule(
            new ElementRule(Taxon.class, 1, Integer.MAX_VALUE),
            new ElementRule(TaxonSet.class, 1, Integer.MAX_VALUE)
        )
    };

    @Override
	public String getParserDescription() {
        return "Defines a set of taxon objects.";
    }

    @Override
	public Class getReturnType() { return TaxonSet.class; }
}


