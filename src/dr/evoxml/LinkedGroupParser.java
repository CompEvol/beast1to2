/*
 * LinkedGroupParser.java
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

import dr.evolution.LinkedGroup;
import dr.evolution.util.Taxa;
import dr.xml.*;

/**
 * @author Aaron Darling
 */
public class LinkedGroupParser extends AbstractXMLObjectParser {


	@Override
	public String getParserDescription() {
		return "A group of metagenome reads linked with some probability";
	}

	
	@Override
	public Class getReturnType() {
		return LinkedGroup.class;
	}


	@Override
	public XMLSyntaxRule[] getSyntaxRules() {
		return rules;
	}


	@Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*
        TaxonSet taxa = null;
        double linkageProbability = 0.9999999999;
        if (xo.hasAttribute("probability")) {
            linkageProbability = xo.getDoubleAttribute("probability");
        }
    	taxa = (TaxonSet)xo.getChild(TaxonSet.class);
    	LinkedGroup lg = new LinkedGroup(taxa, linkageProbability);
		return lg;
	*/
		}

	@Override
	public String getParserName() {
		return "LinkedGroup";
	}
	
	private XMLSyntaxRule[] rules = new XMLSyntaxRule[]{
			new ElementRule(Taxa.class),
	        AttributeRule.newDoubleRule("probability", true, "the probability that the group of reads are linked to each other"),
    };

}
