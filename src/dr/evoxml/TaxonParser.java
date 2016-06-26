/*
 * TaxonParser.java
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

import java.util.LinkedHashMap;
import java.util.Map;

import beast.evolution.alignment.Taxon;
import beast.evolution.tree.TraitSet;
import beast.evolution.tree.TraitSet.Units;
import beast1to2.Beast1to2Converter;
import dr.evolution.util.Date;
import dr.evolution.util.Location;
import dr.util.Attribute;
import dr.xml.*;

/**
 * @author Alexei Drummond
 * @author Andrew Rambaut
 * @version $Id: TaxonParser.java,v 1.2 2005/05/24 20:25:59 rambaut Exp $
 */
public class TaxonParser extends AbstractXMLObjectParser {

	// traits are stored here, to be used later once the tree is parsed
	public static Map<String, TraitSet> traits = new LinkedHashMap<>();

	public final static String TAXON = "taxon";

	public String getParserName() {
		return TAXON;
	}

	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.PI);

		if (dr.xml.XMLParser.ID.contains("\'") && dr.xml.XMLParser.ID.contains("\"")) {
			// unable to handle taxon names that contain both single and double quotes
			// as it won't be possible to wrap it in either.
			throw new XMLParseException("Illegal taxon name, " + dr.xml.XMLParser.ID + ", - contains both single and double quotes");
		}

		beast.evolution.alignment.Taxon taxon = new beast.evolution.alignment.Taxon(xo.getStringAttribute(dr.xml.XMLParser.ID));

		for (int i = 0; i < xo.getChildCount(); i++) {
			Object child = xo.getChild(i);

			if (child instanceof Date) {
				Date date = (Date) child;
				if (date.isBackwards()) {
					if (traits.get("date") == null) {
						TraitSet trait = new TraitSet();
						trait.traitNameInput.setValue("date", trait);
						trait.unitsInput.setValue(date.getUnits().name(), trait);
						trait.traitsInput.setValue(taxon.getID() + "=" +date.toString(), trait);
						traits.put("date", trait);
					} else {
						TraitSet trait = traits.get("date");
						trait.traitsInput.setValue(
								trait.traitsInput.get() +",\n" +
										taxon.getID() + "=" +date.toString(), trait);

					}
				} else {
					if (traits.get("date-forward") == null) {
						TraitSet trait = new TraitSet();
						trait.traitNameInput.setValue("date-forward", trait);
						switch (date.getUnits().name()) {
						case "YEARS":
							trait.unitsInput.setValue("year", trait);
							break;
						case "DAYS":
							trait.unitsInput.setValue("day", trait);
							break;
						case "MONTHS":
							trait.unitsInput.setValue("month", trait);
							break;
						}
						trait.traitsInput.setValue(taxon.getID() + "=" +date.toString(), trait);
						traits.put("date-forward", trait);
					} else {
						TraitSet trait = traits.get("date-forward");
						trait.traitsInput.setValue(
								trait.traitsInput.get() +",\n" +
										taxon.getID() + "=" +date.toString(), trait);

					}
				}
			} else if (child instanceof Location) {
				Location attr = (Location) child;
				if (traits.get("location") == null) {
					TraitSet trait = new TraitSet();
					trait.traitNameInput.setValue("location", trait);
					trait.traitsInput.setValue(taxon.getID() + "=" + attr.getLatitude() + " " + attr.getLongitude(), trait);
					traits.put("location", trait);
				} else {
					TraitSet trait = traits.get("location");
					trait.traitsInput.setValue(
							trait.traitsInput.get() +",\n" +
									taxon.getID() + "=" + attr.getLatitude() + " " + attr.getLongitude(), trait);
				}
			} else if (child instanceof Attribute) {
				final Attribute attr = (Attribute) child;
				if (traits.get(attr.getAttributeName()) == null) {
					TraitSet trait = new TraitSet();
					trait.traitNameInput.setValue(attr.getAttributeName(), trait);
					trait.traitsInput.setValue(taxon.getID() + "=" + attr.getAttributeValue(), trait);
					traits.put(attr.getAttributeName(), trait);
				} else {
					TraitSet trait = traits.get(attr.getAttributeName());
					trait.traitsInput.setValue(
							trait.traitsInput.get() +",\n" +
									taxon.getID() + "=" + attr.getAttributeValue(), trait);

				}
			} else if (child instanceof Attribute[]) {
				System.out.println("attribute arrays in taxon element " + Beast1to2Converter.NIY);
//                Attribute[] attrs = (Attribute[]) child;
//                for (Attribute attr : attrs) {
//                    taxon.setAttribute(attr.getAttributeName(), attr.getAttributeValue());
//                }
			} else {
				throw new XMLParseException("Unrecognized element found in taxon element");
			}
		}

		return taxon;
	}

	public String getParserDescription() {
		return "";
	}

	public Class getReturnType() {
		return Taxon.class;
	}

	public XMLSyntaxRule[] getSyntaxRules() {
		return rules;
	}

	private final XMLSyntaxRule[] rules = {
			new StringAttributeRule(dr.xml.XMLParser.ID, "A unique identifier for this taxon"),
			new ElementRule(Attribute.Default.class, true),
			new ElementRule(Date.class, true),
			new ElementRule(Location.class, true)
	};

}
