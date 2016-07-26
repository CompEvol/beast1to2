package dr.evomodelxml.operators;

import dr.evomodel.continuous.FullyConjugateMultivariateTraitLikelihood;
import dr.evomodel.operators.LatentFactorHamiltonianMC;
import dr.inference.model.LatentFactorModel;
import dr.xml.*;

/**
 * Created by max on 12/2/15.
 */
public class LatentFactorHamiltonianMCParser extends AbstractXMLObjectParser {
    public static final String LATENT_FACTOR_MODEL_HAMILTONIAN_MC="LatentFactorHamiltonianMC";
    public static final String WEIGHT="weight";
    public static final String N_STEPS="nSteps";
    public static final String STEP_SIZE="stepSize";
    public static final String MOMENTUM_SD="momentumSd";
    @Override
    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*
        LatentFactorModel lfm=(LatentFactorModel) xo.getChild(LatentFactorModel.class);
        FullyConjugateMultivariateTraitLikelihood tree=(FullyConjugateMultivariateTraitLikelihood) xo.getChild(FullyConjugateMultivariateTraitLikelihood.class);
        double weight=xo.getDoubleAttribute(WEIGHT);
        CoercionMode mode=CoercionMode.parseMode(xo);
        int nSteps=xo.getIntegerAttribute(N_STEPS);
        double stepSize=xo.getDoubleAttribute(STEP_SIZE);
        double momentumSd= xo.getDoubleAttribute(MOMENTUM_SD);


        return new LatentFactorHamiltonianMC(lfm, tree, weight, mode, stepSize, nSteps, momentumSd);


    */
		}

    @Override
    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private static final XMLSyntaxRule[] rules = {
            AttributeRule.newDoubleRule(WEIGHT),
            AttributeRule.newDoubleRule(STEP_SIZE),
            AttributeRule.newIntegerRule(N_STEPS),
            AttributeRule.newDoubleRule(MOMENTUM_SD),
            new ElementRule(LatentFactorModel.class),
            new ElementRule(FullyConjugateMultivariateTraitLikelihood.class),
    };

    @Override
    public String getParserDescription() {
        return "Hamiltonian Monte Carlo For factors";
    }

    @Override
    public Class getReturnType() {
        return LatentFactorHamiltonianMC.class;
    }

    @Override
    public String getParserName() {
        return LATENT_FACTOR_MODEL_HAMILTONIAN_MC;
    }
}
