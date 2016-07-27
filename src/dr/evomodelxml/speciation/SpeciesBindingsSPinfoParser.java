/*
 * SpeciesBindingsSPinfoParser.java
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

import beast.evolution.alignment.Taxon;
import beast.evolution.alignment.TaxonSet;
import dr.evomodel.speciation.SpeciesBindings;
import dr.xml.*;

/**
 */
public class SpeciesBindingsSPinfoParser extends AbstractXMLObjectParser {
    public static final String SP = "sp";

    @Override
	public String getParserName() {
        return SP;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        Taxon[] taxa = new Taxon[xo.getChildCount()];
        TaxonSet t = new TaxonSet();
        for (int nt = 0; nt < taxa.length; ++nt) {
            taxa[nt] = (Taxon) xo.getChild(nt);
            t.setInputValue("taxon", taxa[nt]);
        }
        t.setID(xo.getId());
        return t;
	}

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return new XMLSyntaxRule[]{
                new ElementRule(Taxon.class, 1, Integer.MAX_VALUE)
        };
    }

    @Override
	public String getParserDescription() {
        return "Taxon in a species tree";
    }

    @Override
	public Class getReturnType() {
        return TaxonSet.class;
    }
}
