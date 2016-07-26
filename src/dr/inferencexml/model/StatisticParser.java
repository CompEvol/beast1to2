/*
 * StatisticParser.java
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

package dr.inferencexml.model;

import dr.inference.model.Statistic;
import dr.inference.model.StatisticList;
import dr.xml.*;

/**
 * @version $Id: StatisticParser.java,v 1.6 2005/05/24 20:26:00 rambaut Exp $
 *
 * @author Alexei Drummond
 * @author Andrew Rambaut
 */
public class StatisticParser extends AbstractXMLObjectParser {
	
	public final static String STATISTIC = "statistic";

	@Override
	public String getParserName() { return STATISTIC; }

	@Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;

//		final StatisticList statList = (StatisticList)xo.getChild(StatisticList.class);
//		final String name = xo.getStringAttribute("name");
//		final Statistic stat = statList.getStatistic(name);
//		if (stat == null) {
//			StringBuffer buffer = new StringBuffer("Unknown statistic name, " + name + "\n");
//			buffer.append("Valid statistics are:");
//			for (int i = 0; i < statList.getStatisticCount(); i++) {
//                buffer.append("\n  ").append(statList.getStatistic(i).getStatisticName());
//			}
//			throw new XMLParseException(buffer.toString());
//		}
//
//		return stat;
	}
	
	//************************************************************************
	// AbstractXMLObjectParser implementation
	//************************************************************************
	
	@Override
	public String getParserDescription() {
		return "A statistic of a given name from the specified object.  ";
	}
	
	@Override
	public Class getReturnType() { return Statistic.class; }
	
	@Override
	public XMLSyntaxRule[] getSyntaxRules() { return rules; }
	
	private final XMLSyntaxRule[] rules = {
		new StringAttributeRule("name", "The name of the statistic you wish to extract from the given object"),
		new ElementRule(StatisticList.class)
	};
}
