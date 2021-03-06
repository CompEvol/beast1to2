/*
 * ExponentialDistributionModelParser.java
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

import beast.core.parameter.RealParameter;
import beast.math.distributions.Exponential;
import beast.math.distributions.ParametricDistribution;

/**
 */
public class ExponentialDistributionModelParser extends DistributionModelParser {

    @Override
	public String getParserName() {
        return "exponentialDistributionModel";
    }

    @Override
	ParametricDistribution parseDistributionModel(RealParameter[] parameters, double offset) {
        Exponential exp = new Exponential();
        exp.initByName("mean", parameters[0], "offset", offset);
        return exp;
    }

    @Override
	public String[] getParameterNames() {
        return new String[]{MEAN};
    }

    @Override
	public String getParserDescription() {
        return "A model of an exponential distribution.";
    }

    @Override
	public boolean allowOffset() {
        return true;
    }

    @Override
	public Class getReturnType() {
        return Exponential.class;
    }
}
