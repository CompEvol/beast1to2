/*
 * SequenceParser.java
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

import java.util.StringTokenizer;

import beast.evolution.alignment.Sequence;
import beast.evolution.alignment.Taxon;
import beast.evolution.datatype.*;
import beast1to2.Beast1to2Converter;;

/**
 * @author Alexei Drummond
 * @author Andrew Rambaut
 *
 * @version $Id: SequenceParser.java,v 1.2 2005/05/24 20:25:59 rambaut Exp $
 */
public class SequenceParser extends AbstractXMLObjectParser {

    public static final String SEQUENCE = "sequence";

    @Override
	public String getParserName() { return SEQUENCE; }

    /**
     * @return a sequence object based on the XML element it was passed.
     */
    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        Sequence sequence = new Sequence();

        Taxon taxon = (Taxon)xo.getChild(Taxon.class);

        DataType dataType = null;
        if (xo.hasAttribute(dr.evolution.datatype.DataType.DATA_TYPE)) {
            String dataTypeStr = xo.getStringAttribute(dr.evolution.datatype.DataType.DATA_TYPE);

            if (dataTypeStr.equals(dr.evolution.datatype.Nucleotides.DESCRIPTION)) {
                dataType = new Nucleotide();
            } else if (dataTypeStr.equals(dr.evolution.datatype.AminoAcids.DESCRIPTION)) {
                dataType = new Aminoacid();
            } else if (dataTypeStr.equals(dr.evolution.datatype.Codons.DESCRIPTION)) {
            	System.out.println("Codon " + Beast1to2Converter.NIY);
                dataType = null;
            } else if (dataTypeStr.equals(dr.evolution.datatype.TwoStates.DESCRIPTION)) {
                dataType = new Binary();
            }
        }

        StringBuffer seqBuf = new StringBuffer();

        for (int i = 0; i < xo.getChildCount(); i++) {
            Object child = xo.getChild(i);
            if (child instanceof String) {
                StringTokenizer st = new StringTokenizer((String)child);
                while (st.hasMoreTokens()) {
                    seqBuf.append(st.nextToken());
                }
            }
        }

        // We really need to filter the input string to check for illegal characters.
        // Perhaps sequence.setSequenceString could throw an exception if any characters
        // don't fit the dataType.
        String sequenceString = seqBuf.toString();

        if (sequenceString.length() == 0) {
            throw new XMLParseException("Sequence data missing from sequence element!");
        }

        if (dataType != null) {
        	System.out.println("data types on taxon are not implemented yet");
        }

        sequence.dataInput.setValue(sequenceString, sequence);
        sequence.taxonInput.setValue(taxon.getID(), sequence);

        return sequence;
		}

    @Override
	public String getParserDescription() {
        return "A biomolecular sequence.";
    }

    @Override
	public Class getReturnType() { return Sequence.class; }

    @Override
	public XMLSyntaxRule[] getSyntaxRules() { return rules; }

    private XMLSyntaxRule[] rules = new XMLSyntaxRule[] {
        new ElementRule(Taxon.class),
        new ElementRule(String.class, "A character string representing the aligned molecular sequence", "ACGACTAGCATCGAGCTTCG--GATAGCATGC")
    };
}

