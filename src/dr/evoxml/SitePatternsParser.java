/*
 * SitePatternsParser.java
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

import dr.xml.*;

import java.util.logging.Logger;

import beast.core.parameter.RealParameter;
import beast.evolution.alignment.Alignment;
import beast.evolution.alignment.FilteredAlignment;
import beast.evolution.alignment.TaxonSet;
import beast1to2.Beast1to2Converter;

/**
 * @author Alexei Drummond
 * @author Andrew Rambaut
 * @author Marc A. Suchard
 * @version $Id: SitePatternsParser.java,v 1.3 2005/07/11 14:06:25 rambaut Exp $
 */
public class SitePatternsParser extends AbstractXMLObjectParser {

    public static final String PATTERNS = "patterns";
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String EVERY = "every";
    public static final String TAXON_LIST = "taxonList";
    public static final String STRIP = "strip";
    public static final String UNIQUE = "unique";
    public static final String CONSTANT_PATTERNS = "constantPatterns";


    @Override
	public String getParserName() {
        return PATTERNS;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

    	Alignment alignment = (Alignment) xo.getChild(Alignment.class);
        TaxonSet taxa = null;

        int from = 1;
        int to = -1;
        int every = xo.getAttribute(EVERY, 1);

        boolean strip = xo.getAttribute(STRIP, true);

        boolean unique = xo.getAttribute(UNIQUE, true);
        if (unique == false) {
        	System.out.println(getParserName() + ".unique " + Beast1to2Converter.NIY);
        }

        if (xo.hasAttribute(FROM)) {
            from = xo.getIntegerAttribute(FROM);
            if (from < 0)
                throw new XMLParseException("illegal 'from' attribute in patterns element");
        }

        if (xo.hasAttribute(TO)) {
            to = xo.getIntegerAttribute(TO);
            if (to < 0 || to < from)
                throw new XMLParseException("illegal 'to' attribute in patterns element");
        }

        if (every <= 0) throw new XMLParseException("illegal 'every' attribute in patterns element");

        if (xo.hasChildNamed(TAXON_LIST)) {
            taxa = (TaxonSet) xo.getElementFirstChild(TAXON_LIST);
        }

        int[] constantPatternCounts = null;
        if (xo.hasChildNamed(CONSTANT_PATTERNS)) {
            RealParameter param = (RealParameter) xo.getElementFirstChild(CONSTANT_PATTERNS);
            if (param.getDimension() != alignment.getDataType().getStateCount()) {
                throw new XMLParseException("The " + CONSTANT_PATTERNS + " parameter length should be equal to the number of states");
            }
            constantPatternCounts = new int[param.getDimension()];
            int i = 0;
            for (double value : param.getValues()) {
                constantPatternCounts[i] = (int)value;
                i++;
            }
        }

        if (from > alignment.getSiteCount())
            throw new XMLParseException("illegal 'from' attribute in patterns element");

        if (to > alignment.getSiteCount())
            throw new XMLParseException("illegal 'to' attribute in patterns element");

        FilteredAlignment patterns = new FilteredAlignment();
        patterns.initByName("data", alignment, 
//        		"taxonset", taxa, 
        		"filter", from+":" + (to >= 0 ? to : alignment.getSiteCount()) +":" + every, 
        		"strip", strip,  
        		"constantSiteWeights", constantPatternCounts);
        patterns.sequenceInput.get().clear();
        
        int f = from + 1;
        int t = to + 1; // fixed a *display* error by adding + 1 for consistency with f = from + 1
        if (to == -1) t = alignment.getSiteCount();

        if (xo.hasAttribute(XMLParser.ID)) {
            final Logger logger = Logger.getLogger("dr.evoxml");
            logger.info("Site patterns '" + xo.getId() + "' created from positions " +
                    Integer.toString(f) + "-" + Integer.toString(t) +
                    " of alignment '" + alignment.getID() + "'");

            if (every > 1) {
                logger.info("  only using every " + every + " site");
            }
            logger.info("  " + (unique ? "unique ": "") + "pattern count = " + patterns.getPatternCount());
        }

        return patterns;
    
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newIntegerRule(FROM, true, "The site position to start at, default is 1 (the first position)"),
            AttributeRule.newIntegerRule(TO, true, "The site position to finish at, must be greater than <b>" + FROM + "</b>, default is length of given alignment"),
            AttributeRule.newIntegerRule(EVERY, true, "Determines how many sites are selected. A value of 3 will select every third site starting from <b>" + FROM + "</b>, default is 1 (every site)"),
            new ElementRule(TAXON_LIST,
                    new XMLSyntaxRule[]{new ElementRule(TaxonSet.class)}, true),
            new ElementRule(CONSTANT_PATTERNS,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}, true),
            new ElementRule(Alignment.class),
            AttributeRule.newBooleanRule(STRIP, true, "Strip out completely ambiguous sites"),
            AttributeRule.newBooleanRule(UNIQUE, true, "Return a weight list of unique patterns"),
    };

    @Override
	public String getParserDescription() {
        return "A weighted list of the unique site patterns (unique columns) in an alignment.";
    }

    @Override
	public Class getReturnType() {
        return FilteredAlignment.class;
    }

}
