/*
 * DistanceMatrixParser.java
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

import dr.evolution.datatype.Nucleotides;
import dr.xml.*;

import java.util.logging.Logger;

import beast.evolution.alignment.Alignment;
import beast.evolution.alignment.distance.Distance;
import beast.evolution.alignment.distance.F84Distance;
import beast.evolution.alignment.distance.HammingDistance;
import beast.evolution.alignment.distance.JukesCantorDistance;
import beast.evolution.alignment.distance.SMMDistance;

/**
 * @author Alexei Drummond
 * @author Andrew Rambaut
 *
 * @version $Id: DistanceMatrixParser.java,v 1.3 2005/07/11 14:06:25 rambaut Exp $
 */
public class DistanceMatrixParser extends AbstractXMLObjectParser {

    public static final String DISTANCE_MATRIX = "distanceMatrix";
    public static final String CORRECTION = "correction";

    @Override
	public String getParserName() { return DISTANCE_MATRIX; }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {

    	Alignment patterns = (Alignment)xo.getChild(Alignment.class);

        Distance matrix = null;

        String type = xo.getStringAttribute(CORRECTION);
        if (type.equals(Nucleotides.JC)) {
	        Logger.getLogger("dr.evoxml").info("Creating Jukes-Cantor distance matrix");
	        matrix = new JukesCantorDistance();
        } else if (type.equals(Nucleotides.F84)) {
	        Logger.getLogger("dr.evoxml").info("Creating F84 distance matrix");
            matrix = new F84Distance();
        } else if (type.equals("SMM")){
            Logger.getLogger("dr.evoxml").info("Creating SMM distance matrix");
            matrix = new SMMDistance();
        } else {
            matrix = new HammingDistance();
        }

        return matrix;
	}

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
        new StringAttributeRule(CORRECTION,
            "The type of distance correction used",
            new String[] { "none", Nucleotides.JC, Nucleotides.F84, "SMM" }, false),
        new ElementRule(Alignment.class)
    };

    @Override
	public String getParserDescription() {
        return "Constructs a distance matrix from a pattern list or alignment";
    }

    @Override
	public Class getReturnType() { return Distance.class; }
}
