/*
 * GeneralizedLinearModelParser.java
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

import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.SingularValueDecomposition;
import dr.inference.distribution.GeneralizedLinearModel;
import dr.inference.model.DesignMatrix;
import dr.inference.model.Likelihood;
import beast.core.parameter.RealParameter;
import dr.xml.*;

/**
 *
 */
public class GeneralizedLinearModelParser extends AbstractXMLObjectParser {

    public static final String GLM_LIKELIHOOD = "glmModel";

    public static final String DEPENDENT_VARIABLES = "dependentVariables";
    public static final String INDEPENDENT_VARIABLES = "independentVariables";
    public static final String BASIS_MATRIX = "basis";
    public static final String FAMILY = "family";
    public static final String SCALE_VARIABLES = "scaleVariables";
    public static final String INDICATOR = "indicator";
    public static final String LOGISTIC_REGRESSION = "logistic";
    public static final String NORMAL_REGRESSION = "normal";
    public static final String LOG_NORMAL_REGRESSION = "logNormal";
    public static final String LOG_LINEAR = "logLinear";
//    public static final String LOG_TRANSFORM = "logDependentTransform";
    public static final String RANDOM_EFFECTS = "randomEffects";
    public static final String CHECK_IDENTIFIABILITY = "checkIdentifiability";
    public static final String CHECK_FULL_RANK = "checkFullRank";

    @Override
	public String getParserName() {
        return GLM_LIKELIHOOD;
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*


        System.err.println("PASSED 0");
        XMLObject cxo = xo.getChild(DEPENDENT_VARIABLES);
        RealParameter dependentParam = null;
        if (cxo != null)
            dependentParam = (RealParameter) cxo.getChild(RealParameter.class);

        String family = xo.getStringAttribute(FAMILY);
        GeneralizedLinearModel glm;
        if (family.compareTo(LOGISTIC_REGRESSION) == 0) {
            glm = new LogisticRegression(dependentParam);
        } else if (family.compareTo(NORMAL_REGRESSION) == 0) {
            glm = new LinearRegression(dependentParam, false);
        } else if (family.compareTo(LOG_NORMAL_REGRESSION) == 0) {
            glm = new LinearRegression(dependentParam, true);
        } else if (family.compareTo(LOG_LINEAR) == 0) {
            glm = new LogLinearModel(dependentParam);
        } else
            throw new XMLParseException("Family '" + family + "' is not currently implemented");

        if (glm.requiresScale()) {
            cxo = xo.getChild(SCALE_VARIABLES);
            RealParameter scaleParameter = null;
//                DesignMatrix designMatrix = null;
            RealParameter scaleDesign = null;
            if (cxo != null) {
                scaleParameter = (RealParameter) cxo.getChild(RealParameter.class);
                XMLObject gxo = cxo.getChild(INDICATOR);
                if (gxo != null)
                    scaleDesign = (RealParameter) gxo.getChild(RealParameter.class);
//                    designMatrix = (DesignMatrix) cxo.getChild(DesignMatrix.class);
            }
            if (scaleParameter == null)
                throw new XMLParseException("Family '" + family + "' requires scale parameters");
            if (scaleDesign == null)
                scaleDesign = new RealParameter.Default(dependentParam.getDimension(), 0.0);
            else {
                if (scaleDesign.getDimension() != dependentParam.getDimension())
                    throw new XMLParseException("Scale ("+dependentParam.getDimension()+") and scaleDesign parameters ("+scaleDesign.getDimension()+") must be the same dimension");
                for (int i = 0; i < scaleDesign.getDimension(); i++) {
                    double value = scaleDesign.getParameterValue(i);
                    if (value < 1 || value > scaleParameter.getDimension())
                        throw new XMLParseException("Invalid scaleDesign value");
                    scaleDesign.setParameterValue(i, value - 1);
                }
            }

            glm.addScaleParameter(scaleParameter, scaleDesign);
        }
        System.err.println("START 0");
        addIndependentParameters(xo, glm, dependentParam);
        System.err.println("START 1");
        addRandomEffects(xo, glm, dependentParam);
        System.err.println("START 2");

        boolean checkIdentifiability = xo.getAttribute(CHECK_IDENTIFIABILITY, true);
        if (checkIdentifiability) {
            if (!glm.getAllIndependentVariablesIdentifiable()) {
                throw new XMLParseException("All design matrix predictors are not identifiable in "+  xo.getId());
            }
        }
        System.err.println("PASSED B");
        checkFullRankOfMatrix = xo.getAttribute(CHECK_FULL_RANK,true);
        System.err.println("PASSED C");
        return glm;
    */
		}

    public void addRandomEffects(XMLObject xo, GeneralizedLinearModel glm,
                                 RealParameter dependentParam) throws XMLParseException {
        int totalCount = xo.getChildCount();

        for (int i = 0; i < totalCount; i++) {
            if (xo.getChildName(i).compareTo(RANDOM_EFFECTS) == 0) {
                XMLObject cxo = (XMLObject) xo.getChild(i);
                RealParameter randomEffect = (RealParameter) cxo.getChild(RealParameter.class);
                checkRandomEffectsDimensions(randomEffect, dependentParam);
                //TODO: fix this: glm.addRandomEffectsParameter(randomEffect);
            }
        }
    }

    public void addIndependentParameters(XMLObject xo, GeneralizedLinearModel glm,
                                         RealParameter dependentParam) throws XMLParseException {
        int totalCount = xo.getChildCount();
//        System.err.println("number of independent parameters = "+totalCount);

        for (int i = 0; i < totalCount; i++) {
            if (xo.getChildName(i).compareTo(INDEPENDENT_VARIABLES) == 0) {
                XMLObject cxo = (XMLObject) xo.getChild(i);
                RealParameter independentParam = (RealParameter) cxo.getChild(RealParameter.class);
                DesignMatrix designMatrix = (DesignMatrix) cxo.getChild(DesignMatrix.class);
                checkDimensions(independentParam, dependentParam, designMatrix);
                cxo = cxo.getChild(INDICATOR);
                RealParameter indicator = null;
                if (cxo != null) {
                    indicator = (RealParameter) cxo.getChild(RealParameter.class);
                    if (indicator.getDimension() != independentParam.getDimension())
                        throw new XMLParseException("dim(" + independentParam.getID() + ") != dim(" + indicator.getID() + ")");
                }
//                System.err.println("A");
                if (checkFullRankOfMatrix) {
                    checkFullRank(designMatrix);
                }
//                System.err.println("B");

//                System.err.println(new Matrix(designMatrix.getParameterAsMatrix()));
//                System.exit(-1);

                // TODO: fix this: glm.addIndependentParameter(independentParam, designMatrix, indicator);
//                System.err.println("C");
            }
        }
    }

    private boolean checkFullRankOfMatrix;

    private void checkFullRank(DesignMatrix designMatrix) throws XMLParseException {
        int fullRank = designMatrix.getColumnDimension();
//        System.err.println("designMatrix getColumnDimension = "+fullRank);
        SingularValueDecomposition svd = new SingularValueDecomposition(
                new DenseDoubleMatrix2D(designMatrix.getParameterAsMatrix()));
        int realRank = svd.rank();
        if (realRank != fullRank) {
            throw new XMLParseException(
                "rank(" + designMatrix.getId() + ") = " + realRank +
                        ".\nMatrix is not of full rank as colDim(" + designMatrix.getId() + ") = " + fullRank        
            );
        }
    }

    private void checkRandomEffectsDimensions(RealParameter randomEffect, RealParameter dependentParam)
            throws XMLParseException {
        if (dependentParam != null) {
            if (randomEffect.getDimension() != dependentParam.getDimension()) {
                throw new XMLParseException(
                        "dim(" + dependentParam.getID() + ") != dim(" + randomEffect.getID() + ")"
                );
            }
        }
    }

    private void checkDimensions(RealParameter independentParam, RealParameter dependentParam, DesignMatrix designMatrix)
            throws XMLParseException {
        if (dependentParam != null) {
            if ((dependentParam.getDimension() != designMatrix.getRowDimension()) ||
                    (independentParam.getDimension() != designMatrix.getColumnDimension()))
                throw new XMLParseException(
                        "dim(" + dependentParam.getID() + ") != dim(" + designMatrix.getId() + " %*% " + independentParam.getID() + ")"
                );
        } else {
            if (independentParam.getDimension() != designMatrix.getColumnDimension()) {
                throw new XMLParseException(
                        "dim(" + independentParam.getID() + ") is incompatible with dim (" + designMatrix.getId() + ")"
                );
            }
//            System.err.println(independentParam.getId()+" and "+designMatrix.getId());
        }
    }

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            AttributeRule.newStringRule(FAMILY),
            AttributeRule.newBooleanRule(CHECK_IDENTIFIABILITY, true),
            AttributeRule.newBooleanRule(CHECK_FULL_RANK, true),
            new ElementRule(DEPENDENT_VARIABLES,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}, true),
            new ElementRule(INDEPENDENT_VARIABLES,
                    new XMLSyntaxRule[]{
                            new ElementRule(RealParameter.class, true),
                            new ElementRule(DesignMatrix.class),
                            new ElementRule(INDICATOR,
                                    new XMLSyntaxRule[]{
                                            new ElementRule(RealParameter.class)
                                    }, true),
                    }, 1, 3),
            new ElementRule(RANDOM_EFFECTS,
                    new XMLSyntaxRule[]{new ElementRule(RealParameter.class)}, 0, 3),
//				new ElementRule(BASIS_MATRIX,
//						new XMLSyntaxRule[]{new ElementRule(DesignMatrix.class)})
    };

    @Override
	public String getParserDescription() {
        return "Calculates the generalized linear model likelihood of the dependent parameters given one or more blocks of independent parameters and their design matrix.";
    }

    @Override
	public Class getReturnType() {
        return Likelihood.class;
    }
}
