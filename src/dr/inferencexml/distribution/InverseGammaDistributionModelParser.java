/*
 * InverseGammaDistributionModelParser.java
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
import beast.math.distributions.InverseGamma;
import beast.math.distributions.ParametricDistribution;

/**
 */
public class InverseGammaDistributionModelParser extends DistributionModelParser {

    @Override
	public String getParserName() {
        return "inverseGammaDistributionModel";
    }

    @Override
	ParametricDistribution parseDistributionModel(RealParameter[] parameters, double offset) {
        InverseGamma igamma = new InverseGamma();
        igamma.initByName("shape", parameters[0], "scale", parameters[1]);
        return igamma;
    }

    @Override
	public String[] getParameterNames() {
        return new String[]{SHAPE, SCALE};
    }

    @Override
	public String getParserDescription() {
        return "A model of an inverese gamma distribution.";
    }

    @Override
	public boolean allowOffset() {
        return false;
    }

    @Override
	public Class getReturnType() {
        return InverseGamma.class;
    }
}
