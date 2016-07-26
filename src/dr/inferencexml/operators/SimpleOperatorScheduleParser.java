/*
 * SimpleOperatorScheduleParser.java
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

package dr.inferencexml.operators;


import beast.core.Operator;
import beast.core.OperatorSchedule;
import beast.core.util.Log;
import dr.xml.*;

/**
 *
 */
public class SimpleOperatorScheduleParser extends AbstractXMLObjectParser {

    public static final String OPERATOR_SCHEDULE = "operators";
    public static final String SEQUENTIAL = "sequential";
    public static final String OPTIMIZATION_SCHEDULE = "optimizationSchedule";

    @Override
	public String getParserName() {
        return OPERATOR_SCHEDULE;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        OperatorSchedule schedule = new OperatorSchedule();

        if (xo.hasAttribute(SEQUENTIAL)) {
            throw new UnsupportedOperationException(getParserName() + " " +
                    SEQUENTIAL + " " + beast1to2.Beast1to2Converter.NIY);
        }
        if (xo.hasAttribute(OPTIMIZATION_SCHEDULE)) {
        	if (!xo.getAttribute(OPTIMIZATION_SCHEDULE).equals("default")) {
        		Log.warning.println(getParserName() + " " +
                    OPTIMIZATION_SCHEDULE + " " + beast1to2.Beast1to2Converter.NIY);
        	}
        }

        // BEAST 2 default to autoOptimise
        for (int i = 0; i < xo.getChildCount(); i++) {
            Object child = xo.getChild(i);
            if (child instanceof Operator) {
                schedule.addOperator((Operator) child);
            }
        }
        schedule.initAndValidate();

        return schedule;

		/*

        SimpleOperatorSchedule schedule = new SimpleOperatorSchedule();

        if (xo.hasAttribute(SEQUENTIAL)) {
            schedule.setSequential(xo.getBooleanAttribute(SEQUENTIAL));
        }


        if (xo.hasAttribute(OPTIMIZATION_SCHEDULE)) {
            String type = xo.getStringAttribute(OPTIMIZATION_SCHEDULE);
            Logger.getLogger("dr.inference").info("Optimization Schedule: " + type);

            try {
                schedule.setOptimizationSchedule(OperatorSchedule.OptimizationTransform.valueOf(type.toUpperCase()));
            } catch (IllegalArgumentException iae) {
                throw new RuntimeException("Unsupported optimization schedule");
            }
        }

        for (int i = 0; i < xo.getChildCount(); i++) {
            Object child = xo.getChild(i);
            if (child instanceof MCMCOperator) {
                schedule.addOperator((MCMCOperator) child);
            }
        }
        return schedule;
    */
		}

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newBooleanRule(SEQUENTIAL, true),
            new ElementRule(Operator.class, 1, Integer.MAX_VALUE),
            AttributeRule.newStringRule(OPTIMIZATION_SCHEDULE, true)
    };

    @Override
	public String getParserDescription() {
        return "A simple operator scheduler";
    }

    @Override
	public Class getReturnType() {
        return OperatorSchedule.class;
    }
    
}
