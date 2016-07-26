/*
 * PriorParsers.java
 *
 * Copyright (c) 2002-2016 Alexei Drummond, Andrew Rambaut and Marc Suchard
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

import beast.core.Function;
import beast.core.parameter.Parameter;
import beast.core.parameter.RealParameter;
import beast.math.distributions.Dirichlet;
import beast.math.distributions.Exponential;
import beast.math.distributions.Gamma;
import beast.math.distributions.InverseGamma;
import beast.math.distributions.LaplaceDistribution;
import beast.math.distributions.LogNormalDistributionModel;
import beast.math.distributions.MRCAPrior;
import beast.math.distributions.Normal;
import beast.math.distributions.ParametricDistribution;
import beast.math.distributions.Poisson;
import beast.math.distributions.Prior;
import beast.math.distributions.Uniform;
import dr.inference.distribution.MultivariateDistributionLikelihood;
import dr.inference.model.Likelihood;
import dr.xml.*;

/**
 */
public class PriorParsers {
    public final static boolean DEBUG = false;

    public static final String UNIFORM_PRIOR = "uniformPrior";
    public static final String EXPONENTIAL_PRIOR = "exponentialPrior";
    public static final String POISSON_PRIOR = "poissonPrior";
    public static final String NEGATIVE_BINOMIAL_PRIOR = "negativeBinomialPrior";
    public static final String DISCRETE_UNIFORM_PRIOR = "discreteUniformPrior";
    public static final String NORMAL_PRIOR = "normalPrior";
    public static final String LOG_NORMAL_PRIOR = "logNormalPrior";
    public static final String GAMMA_PRIOR = "gammaPrior";
    public static final String INVGAMMA_PRIOR = "invgammaPrior";
    public static final String INVGAMMA_PRIOR_CORRECT = "inverseGammaPrior";
    public static final String LAPLACE_PRIOR = "laplacePrior";
    public static final String BETA_PRIOR = "betaPrior";
    public static final String UPPER = "upper";
    public static final String LOWER = "lower";
    public static final String MEAN = "mean";
    public static final String MEAN_IN_REAL_SPACE = "meanInRealSpace";
    public static final String STDEV = "stdev";
    public static final String SHAPE = "shape";
    public static final String SHAPEB = "shapeB";
    public static final String SCALE = "scale";
    public static final String DF = "df";
    public static final String OFFSET = "offset";
    public static final String UNINFORMATIVE = "uninformative";
    public static final String HALF_T_PRIOR = "halfTPrior";
    public static final String DIRICHLET_PRIOR = "dirichletPrior";
    public static final String COUNTS = "counts";


    /**
     * A special parser that reads a convenient short form of priors on parameters.
     */
    public static XMLObjectParser UNIFORM_PRIOR_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return UNIFORM_PRIOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {

            double lower = xo.getDoubleAttribute(LOWER);
            double upper = xo.getDoubleAttribute(UPPER);

            if (lower == Double.NEGATIVE_INFINITY || upper == Double.POSITIVE_INFINITY)
                throw new XMLParseException("Uniform prior " + xo.getName() + " cannot take a bound at infinity, " +
                        "because it returns 1/(high-low) = 1/inf");

            Uniform distr = new Uniform();
            distr.initByName("lower", lower, "upper", upper);
            
            
            if (DEBUG) {
                System.out.println("Uniform prior: " + xo.getChildCount());
            }
            Function x = null;
            for (int j = 0; j < xo.getChildCount(); j++) {
                if (DEBUG) {
                    System.out.println(xo.getChild(j));
                }
                if (xo.getChild(j) instanceof Function) {
                	x = (RealParameter) xo.getChild(j);
                } else {
                    throw new XMLParseException("illegal element in " + xo.getName() + " element");
                }
            }

            return prior(xo, distr);
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(LOWER),
                AttributeRule.newDoubleRule(UPPER),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a given uniform distribution.";
        }

        @Override
		public Class getReturnType() {
            return Likelihood.class;
        }
    };

    /**
     * A special parser that reads a convenient short form of priors on parameters.
     */
    public static XMLObjectParser EXPONENTIAL_PRIOR_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return EXPONENTIAL_PRIOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {

            double scale;

            if (xo.hasAttribute(SCALE)) {
                scale = xo.getDoubleAttribute(SCALE);
            } else {
                scale = xo.getDoubleAttribute(MEAN);
            }
            final double offset = xo.hasAttribute(OFFSET) ? xo.getDoubleAttribute(OFFSET) : 0.0;

            Exponential exp = new Exponential();
            exp.initByName("mean", scale + "", "offset", offset);
            return prior(xo, exp);
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                new XORRule(
                        AttributeRule.newDoubleRule(SCALE),
                        AttributeRule.newDoubleRule(MEAN)
                ),
                AttributeRule.newDoubleRule(OFFSET, true),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a given exponential distribution.";
        }

        @Override
		public Class getReturnType() {
            return Likelihood.class;
        }
    };

    /**
     * A special parser that reads a convenient short form of priors on parameters.
     */
    public static XMLObjectParser POISSON_PRIOR_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return POISSON_PRIOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {

            double mean = xo.getDoubleAttribute(MEAN);
            double offset = xo.getDoubleAttribute(OFFSET);
            Poisson poisson = new Poisson();
            poisson.initByName("lambda", mean + "", "offset", offset);
            return prior(xo, poisson);
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(MEAN),
                AttributeRule.newDoubleRule(OFFSET),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a given poisson distribution.";
        }

        @Override
		public Class getReturnType() {
            return Likelihood.class;
        }
    };

    /**
     * A special parser that reads a convenient short form of priors on parameters.
     */
    public static XMLObjectParser NEGATIVE_BINOMIAL_PRIOR_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return NEGATIVE_BINOMIAL_PRIOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

            double mean = xo.getDoubleAttribute(MEAN);
            double stdev = xo.getDoubleAttribute(STDEV);

            DistributionLikelihood likelihood = new DistributionLikelihood(new NegativeBinomialDistribution(mean, stdev));
            for (int j = 0; j < xo.getChildCount(); j++) {
                if (xo.getChild(j) instanceof Statistic) {
                    likelihood.addData((Statistic) xo.getChild(j));
                } else {
                    throw new XMLParseException("illegal element in " + xo.getName() + " element");
                }
            }

            return likelihood;
        */
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(MEAN),
                AttributeRule.newDoubleRule(STDEV),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a given negative binomial distribution.";
        }

        @Override
		public Class getReturnType() {
            return Likelihood.class;
        }
    };

    /**
     * A special parser that reads a convenient short form of priors on parameters.
     */
    public static XMLObjectParser DISCRETE_UNIFORM_PRIOR_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return DISCRETE_UNIFORM_PRIOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {

            double upper = xo.getDoubleAttribute(UPPER);
            double lower = xo.getDoubleAttribute(LOWER);
            Uniform uniform = new Uniform();
            uniform.initByName("lower", lower, "upper", upper);

            return prior(xo, uniform);
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(LOWER),
                AttributeRule.newDoubleRule(UPPER),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a given discrete uniform distribution.";
        }

        @Override
		public Class getReturnType() {
            return Likelihood.class;
        }
    };



    /**
     * A special parser that reads a convenient short form of priors on parameters.
     */
    public static XMLObjectParser HALF_T_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return HALF_T_PRIOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*

            double scale = xo.getDoubleAttribute(SCALE);
            double df = xo.getDoubleAttribute(DF);

            DistributionLikelihood likelihood = new DistributionLikelihood(new HalfTDistribution(scale, df));
            for (int j = 0; j < xo.getChildCount(); j++) {
                if (xo.getChild(j) instanceof Statistic) {
                    likelihood.addData((Statistic) xo.getChild(j));
                } else {
                    throw new XMLParseException("illegal element in " + xo.getName() + " element");
                }
            }

            return likelihood;
        */
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(SCALE),
                AttributeRule.newDoubleRule(DF),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a given half-T distribution.";
        }

        @Override
		public Class getReturnType() {
            return Likelihood.class;
        }
    };

    /**
     * A special parser that reads a convenient short form of priors on parameters.
     */
    public static XMLObjectParser NORMAL_PRIOR_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return NORMAL_PRIOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {
            double mean = xo.getDoubleAttribute(MEAN);
            double stdev = xo.getDoubleAttribute(STDEV);
            Normal normal = new Normal();
            normal.initByName("mean", mean + "", "sigma", stdev + "");

            return prior(xo, normal);
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(MEAN),
                AttributeRule.newDoubleRule(STDEV),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a given normal distribution.";
        }

        @Override
		public Class getReturnType() {
            return Likelihood.class;
        }
    };

    /**
     * A special parser that reads a convenient short form of priors on parameters.
     * <p/>
     * If X ~ logNormal, then log(X) ~ Normal.
     * <br>
     * <br>
     * If meanInRealSpace=false, <code>mean</code> specifies the mean of log(X) and
     * <code>stdev</code> specifies the standard deviation of log(X).
     * <br>
     * <br>
     * If meanInRealSpace=true, <code>mean</code> specifies the mean of X, but <code>
     * stdev</code> specifies the standard deviation of log(X).
     * <br>
     */
    public static XMLObjectParser LOG_NORMAL_PRIOR_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return LOG_NORMAL_PRIOR;
        }


        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {
           double mean = xo.getDoubleAttribute(MEAN);
            final double stdev = xo.getDoubleAttribute(STDEV);
            final double offset = xo.getAttribute(OFFSET, 0.0);
            final boolean meanInRealSpace = xo.getAttribute(MEAN_IN_REAL_SPACE, false);

            if (meanInRealSpace) {
                if (mean <= 0) {
                    throw new IllegalArgumentException("meanInRealSpace works only for a positive mean");
                }
                mean = Math.log(mean) - 0.5 * stdev * stdev;
            }

            LogNormalDistributionModel distr = new LogNormalDistributionModel();
            distr.initByName("M", mean + "", "S", stdev + "", "offset", offset, "meanInRealSpace", meanInRealSpace);
            
            
            return prior(xo, distr);
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(MEAN),
                AttributeRule.newDoubleRule(STDEV),
                AttributeRule.newDoubleRule(OFFSET, true),
                AttributeRule.newBooleanRule(MEAN_IN_REAL_SPACE, true),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a given lognormal distribution.";
        }

        @Override
		public Class getReturnType() {
            return Likelihood.class;
        }
    };


    /**
     * A special parser that reads a convenient short form of priors on parameters.
     */
    public static XMLObjectParser GAMMA_PRIOR_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return GAMMA_PRIOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {


            final double shape = xo.getDoubleAttribute(SHAPE);
            final double scale = xo.getDoubleAttribute(SCALE);
            final double offset = xo.getAttribute(OFFSET, 0.0);

            Gamma gamma = new Gamma();
            gamma.initByName("alpha", shape +"", "beta", scale +"", "offset", offset);
            return prior(xo, gamma);
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(SHAPE),
                AttributeRule.newDoubleRule(SCALE),
                AttributeRule.newDoubleRule(OFFSET, true),
                // AttributeRule.newBooleanRule(UNINFORMATIVE, true),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a given gamma distribution.";
        }

        @Override
		public Class getReturnType() {
            return Likelihood.class;
        }
    };

    /**
     * A special parser that reads a convenient short form of priors on parameters.
     */
    public static XMLObjectParser INVGAMMA_PRIOR_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return INVGAMMA_PRIOR;
        }

        @Override
		public String[] getParserNames() {
            return new String[]{INVGAMMA_PRIOR, INVGAMMA_PRIOR_CORRECT};
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {

            final double shape = xo.getDoubleAttribute(SHAPE);
            final double scale = xo.getDoubleAttribute(SCALE);
            final double offset = xo.getAttribute(OFFSET, 0.0);
            InverseGamma invgamma = new InverseGamma();
            invgamma.initByName("alpha", shape+"", "beta", scale +"", "offset", offset);
            return prior(xo, invgamma);
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(SHAPE),
                AttributeRule.newDoubleRule(SCALE),
                AttributeRule.newDoubleRule(OFFSET, true),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a given inverse gamma distribution.";
        }

        @Override
		public Class getReturnType() {
            return Likelihood.class;
        }
    };

    public static XMLObjectParser LAPLACE_PRIOR_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return LAPLACE_PRIOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {

            double mean = xo.getDoubleAttribute(MEAN);
            double scale = xo.getDoubleAttribute(SCALE);

            LaplaceDistribution laplace = new LaplaceDistribution();
            laplace.initByName("mu", mean +"", "scale", scale+"");
            return prior(xo, laplace);
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(MEAN),
                AttributeRule.newDoubleRule(SCALE),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a given laplace distribution.";
        }

        @Override
		public Class getReturnType() {
            return Likelihood.class;
        }
    };

    /**
     * A special parser that reads a convenient short form of priors on parameters.
     */
    public static XMLObjectParser BETA_PRIOR_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return BETA_PRIOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {

            final double shape = xo.getDoubleAttribute(SHAPE);
            final double shapeB = xo.getDoubleAttribute(SHAPEB);
            final double offset = xo.getAttribute(OFFSET, 0.0);
            final double scale = xo.getAttribute(SCALE, 1.0);
            Gamma gamma = new Gamma();
            gamma.initByName("alpha", shape+"", "beta", scale+"", "offset", offset);
            return prior(xo, gamma);
		}


		@Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleRule(SHAPE),
                AttributeRule.newDoubleRule(SHAPEB),
                AttributeRule.newDoubleRule(OFFSET, true),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a given beta distribution.";
        }

        @Override
		public Class getReturnType() {
            return Likelihood.class;
        }
    };

    /**
     * A special parser that reads a convenient short form of priors on parameters.
     */
    public static XMLObjectParser DIRICHLET_PRIOR_PARSER = new AbstractXMLObjectParser() {

        @Override
		public String getParserName() {
            return DIRICHLET_PRIOR;
        }

        @Override
		public Object parseXMLObject(XMLObject xo) throws XMLParseException {

            double[] counts = xo.getDoubleArrayAttribute(COUNTS);
            Double [] alpha = new Double[counts.length];
            for (int i = 0; i < counts.length; i++) {
            	alpha[i] = counts[i];
            }

			Dirichlet dirichlet = new Dirichlet();
			dirichlet.initByName("alpha", new RealParameter(alpha));
			return prior(xo, dirichlet);
		}

        @Override
		public XMLSyntaxRule[] getSyntaxRules() {
            return rules;
        }

        private final XMLSyntaxRule[] rules = {
                AttributeRule.newDoubleArrayRule(COUNTS),
                new ElementRule(Function.class, 1, Integer.MAX_VALUE)
        };

        @Override
		public String getParserDescription() {
            return "Calculates the prior probability of some data under a Dirichlet distribution.";
        }

        @Override
		public Class getReturnType() {
            return MultivariateDistributionLikelihood.class;
        }
    };

    private static Object prior(XMLObject xo, ParametricDistribution distr) throws XMLParseException {
        Function x = null;
        for (int j = 0; j < xo.getChildCount(); j++) {
            if (xo.getChild(j) instanceof Function) {
            	x = (Function) xo.getChild(j);
            } else {
                throw new XMLParseException("illegal element in " + xo.getName() + " element");
            }
        }

        if (x instanceof MRCAPrior) {
        	MRCAPrior prior = (MRCAPrior) x;
        	prior.distInput.setValue(distr, prior);
         	return prior;
        }

        Prior prior = new Prior();
        prior.initByName("distr", distr, "x", x);
        return prior;
	}

}
