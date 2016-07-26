/*
 * ParameterChooserParser.java
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

import dr.inference.model.ParameterChooser;
import dr.inference.model.ValuesPool;
import dr.xml.*;

/**
 * @author Joseph Heled
 *         Date: 4/09/2009
 */
public class ParameterChooserParser extends AbstractXMLObjectParser {
    public static String VARIABLE_SELECTOR = "variableSelector";
    public static String INDEX = "index";

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
        return null;

//        String name = xo.getId();
//        final ValuesPool pool = (ValuesPool) xo.getChild(ValuesPool.class);
//        final int[] which = xo.getIntegerArrayAttribute(INDEX);
//        for( int w : which ) {
//            if( ! (0 <= w && w < pool.length()) ) {
//                throw new XMLParseException("index " + w + " out of range");
//            }
//        }
//        return new ParameterChooser(name, pool, which);
    }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return new XMLSyntaxRule[] {
                new ElementRule(ValuesPool.class,1,1),
                AttributeRule.newIntegerArrayRule(INDEX, false),
        };
    }

    @Override
	public String getParserDescription() {
        return "";
    }

    @Override
	public Class getReturnType() {
        return ParameterChooser.class;
    }

    @Override
	public String getParserName() {
        return VARIABLE_SELECTOR;
    }
}
