/*
 * MomentDistributionModelParser.java
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

package dr.inferencexml.distribution;

import dr.inference.distribution.MomentDistributionModel;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 * Created by max on 5/14/15.
 */
public class MomentDistributionModelParser extends AbstractXMLObjectParser {
    public static final String MOMENT_DISTRIBUTION_MODEL = "momentDistributionModel";
    public static final String MEAN = "mean";
//    public static final String STDEV = "stdev";
    public static final String PREC = "precision";
    public static final String CUTOFF="cutoff";
    public static final String DATA="data";

    @Override
    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*
        RealParameter mean=(RealParameter) xo.getChild(MEAN).getChild(0);
        RealParameter prec=(RealParameter) xo.getChild(PREC).getChild(0);
        RealParameter cutoff=null;
        if(xo.getChild(CUTOFF) != null){
        cutoff=(RealParameter) xo.getChild(CUTOFF).getChild(0);}
        RealParameter data=(RealParameter) xo.getChild(DATA).getChild(0);

        return new MomentDistributionModel(mean, prec, cutoff, data);
    */
		}

    @Override
    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(MEAN,
                    new XMLSyntaxRule[]{
                                            new ElementRule(RealParameter.class)
                            }
            ),
                    new ElementRule(CUTOFF,
                            new XMLSyntaxRule[]{
                                            new ElementRule(RealParameter.class, true)
                                    },true
                    ),
                    new ElementRule(PREC,
                            new XMLSyntaxRule[]{
                                            new ElementRule(RealParameter.class)
                                    }
            ),
            new ElementRule(DATA,
                    new XMLSyntaxRule[]{
                            new ElementRule(RealParameter.class)
                    }
            )
    };

    @Override
    public String getParserDescription() {
        return "Returns an internally truncated normal distribution for claculating a moment prior";
    }

    @Override
    public Class getReturnType() {
        return MomentDistributionModel.class;
    }

    @Override
    public String getParserName() {
        return MOMENT_DISTRIBUTION_MODEL;
    }
}
