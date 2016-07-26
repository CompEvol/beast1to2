/*
 * SpeciationLikelihoodParser.java
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

package dr.evomodelxml.speciation;

import dr.xml.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import beast.core.Function;
import beast.evolution.alignment.Taxon;
import beast.evolution.alignment.TaxonSet;
import beast.evolution.speciation.BirthDeathGernhard08Model;
import beast.evolution.speciation.CalibratedYuleModel;
import beast.evolution.tree.Tree;
import beast.evolution.tree.TreeDistribution;
import beast.math.distributions.MRCAPrior;
import beast.math.distributions.ParametricDistribution;
import beast1to2.Beast1to2Converter;

/**
 */
public class SpeciationLikelihoodParser extends AbstractXMLObjectParser {

    public static final String SPECIATION_LIKELIHOOD = "speciationLikelihood";
    public static final String MODEL = "model";
    public static final String TREE = "speciesTree";
    public static final String INCLUDE = "include";
    public static final String EXCLUDE = "exclude";

    public static final String CALIBRATION = "calibration";
    public static final String CORRECTION = "correction";
    public static final String POINT = "point";

    private final String EXACT = "exact";
    private final String APPROX = "approximated";
    private final String PEXACT = "pexact";
    private final String NONE = "none";

    public static final String PARENT = dr.evomodelxml.tree.TMRCAStatisticParser.PARENT;

    public String getParserName() {
        return SPECIATION_LIKELIHOOD;
    }

    public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        XMLObject cxo = xo.getChild(MODEL);
        TreeDistribution specModel = (TreeDistribution) cxo.getChild(TreeDistribution.class);

        cxo = xo.getChild(TREE);
        final Tree tree = (Tree) cxo.getChild(Tree.class);
        specModel.treeInput.setValue(tree, specModel);

        Set<Taxon> excludeTaxa = null;

        if (xo.hasChildNamed(INCLUDE)) {
            excludeTaxa = new HashSet<Taxon>();
            TaxonSet s = tree.getTaxonset();
            for (Taxon taxon : s.getTaxonSet()) {
                excludeTaxa.add(taxon);
            }

            cxo = xo.getChild(INCLUDE);
            for (int i = 0; i < cxo.getChildCount(); i++) {
                TaxonSet taxonList = (TaxonSet) cxo.getChild(i);
                for (Taxon taxon : taxonList.getTaxonSet()) {
                    excludeTaxa.remove(taxon);
                }
            }
        }

        if (xo.hasChildNamed(EXCLUDE)) {
            excludeTaxa = new HashSet<Taxon>();
            cxo = xo.getChild(EXCLUDE);
            for (int i = 0; i < cxo.getChildCount(); i++) {
            	TaxonSet taxonList = (TaxonSet) cxo.getChild(i);
                for (Taxon taxon : taxonList.getTaxonSet()) {
                    excludeTaxa.add(taxon);
                }
            }
        }
        if (excludeTaxa != null) {
            Logger.getLogger("dr.evomodel").info("Speciation model excluding " + excludeTaxa.size() + " taxa from prior - " +
                    (tree.getLeafNodeCount() - excludeTaxa.size()) + " taxa remaining.");
        }

        final XMLObject cal = xo.getChild(CALIBRATION);
        if( cal != null ) {
            if( excludeTaxa != null ) {
                throw new XMLParseException("Sorry, not implemented: internal calibration prior + excluded taxa");
            }

            List<ParametricDistribution> dists = new ArrayList<ParametricDistribution>();
            List<TaxonSet> taxa = new ArrayList<>();
            List<Boolean> forParent = new ArrayList<Boolean>();
            Function userPDF = null; // (Statistic) cal.getChild(Statistic.class);

            for(int k = 0; k < cal.getChildCount(); ++k) {
                final Object ck = cal.getChild(k);
//                if ( DistributionLikelihood.class.isInstance(ck) ) {
//                    dists.add( ((DistributionLikelihood) ck).getDistribution() );
//                } else 
                	if ( ParametricDistribution.class.isInstance(ck) ) {
                    dists.add((ParametricDistribution) ck);
                } else if ( TaxonSet.class.isInstance(ck) ) {
                    final TaxonSet tx = (TaxonSet) ck;
                    taxa.add(tx);
                    forParent.add( tx.getTaxonCount() == 1 );
                } else if ( Function.class.isInstance(ck) ) {
                    if( userPDF != null ) {
                        throw new XMLParseException("more than one userPDF correction???");
                    }
                    userPDF = (Function) cal.getChild(Function.class);
                    if (userPDF != null) {
                        throw new XMLParseException(Beast1to2Converter.NIY + " userPDF not supported");
                    }
                }
                else {
                    XMLObject cko = (XMLObject) ck;
                    assert cko.getChildCount() == 2;

                    for(int i = 0; i < 2; ++i) {
                        final Object chi = cko.getChild(i);
//                        if ( DistributionLikelihood.class.isInstance(chi) ) {
//                            dists.add( ((DistributionLikelihood) chi).getDistribution() );
//                        } else 
                        if ( ParametricDistribution.class.isInstance(chi) ) {
                            dists.add((ParametricDistribution) chi);
                        } else if ( TaxonSet.class.isInstance(chi) ) {
                            taxa.add((TaxonSet) chi);
                            boolean fp = ((TaxonSet) chi).getTaxonCount() == 1;
                            if( cko.hasAttribute(PARENT) ) {
                                boolean ufp = cko.getBooleanAttribute(PARENT);
                                if( fp && ! ufp ) {
                                   throw new XMLParseException("forParent==false for a single taxon?? (must be true)");
                                }
                                fp = ufp;
                            }
                            forParent.add(fp);
                        } else {
                            assert false;
                        }
                    }
                }
            }

            if( dists.size() != taxa.size() ) {
                throw new XMLParseException("Mismatch in number of distributions and taxa specs");
            }

            try {
                final String correction = cal.getAttribute(CORRECTION, EXACT);

                final CorrectionType type = correction.equals(EXACT) ? CorrectionType.EXACT :
                        (correction.equals(APPROX) ? CorrectionType.APPROXIMATED :
                                (correction.equals(NONE) ? CorrectionType.NONE :
                                        (correction.equals(PEXACT) ? CorrectionType.PEXACT :  null)));

                if (type == CorrectionType.EXACT && specModel.getID().equals("yule")) {
                	BirthDeathGernhard08Model yule = (BirthDeathGernhard08Model) specModel;
                	CalibratedYuleModel cYule = new CalibratedYuleModel();
                	cYule.initByName("tree", tree, "birthRate", yule.birthDiffRateParameterInput.get());
                	specModel = cYule;
                }
                
                if( cal.hasAttribute(CORRECTION) && type == null ) {
                   throw new XMLParseException("correction type == " + correction + "???");
                }

                MRCAPrior prior = new MRCAPrior();
                if (dists.size() == 0) {
                	dists.add(null);
                }
                if (dists.size() > 1) {
                	throw new XMLParseException(Beast1to2Converter.NIY + " only one distribution per taxonset is supported");
                }
                prior.initByName("tree", tree, "distr", dists.get(0), "taxonset", taxa.get(0), "useOriginate", forParent.get(0));
                
//                final CalibrationPoints calib =
//                        new CalibrationPoints(tree, specModel.isYule(), dists, taxa, forParent, userPDF, type);
//                final SpeciationLikelihood speciationLikelihood = new SpeciationLikelihood(tree, specModel, null, calib);
//                return speciationLikelihood;
            } catch( IllegalArgumentException e ) {
                throw new XMLParseException( e.getMessage() );
            }
        }
        
        specModel.initAndValidate();
        return specModel;
//        return new SpeciationLikelihood(tree, specModel, excludeTaxa, null);
	}
    
    public static enum CorrectionType {
        EXACT("exact"),
        APPROXIMATED("approximated"),
        PEXACT("pexact"),
        NONE("none");

        CorrectionType(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

        private final String name;
    }

    //************************************************************************
    // AbstractXMLObjectParser implementation
    //************************************************************************

    public String getParserDescription() {
        return "This element represents the likelihood of the tree given the speciation.";
    }

    public Class getReturnType() {
        return TreeDistribution.class;
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] calibrationPoint = {
            AttributeRule.newBooleanRule(PARENT, true),
//            new XORRule(
                    new ElementRule(ParametricDistribution.class),
//                    new ElementRule(DistributionLikelihood.class)),
            new ElementRule(TaxonSet.class)
    };

    private final XMLSyntaxRule[] calibration = {
//            AttributeRule.newDoubleArrayRule(COEFFS,true, "use log(lam) -lam * c[0] + sum_k=1..n (c[k+1] * e**(-k*lam*x)) " +
//                    "as a calibration correction instead of default - used when additional constarints are put on the topology."),
            AttributeRule.newStringRule(CORRECTION, true),
            new ElementRule(Function.class, true),
//            new XORRule(
                    new ElementRule(ParametricDistribution.class, 1, 100),
//                    new ElementRule(DistributionLikelihood.class, 1, 100)),
            new ElementRule(TaxonSet.class, 1, 100),
            new ElementRule("point", calibrationPoint, 0, 100)
    };

    private final XMLSyntaxRule[] rules = {
            new ElementRule(MODEL, new XMLSyntaxRule[]{
                    new ElementRule(TreeDistribution.class)
            }),
            new ElementRule(TREE, new XMLSyntaxRule[]{
                    new ElementRule(Tree.class)
            }),

            new ElementRule(INCLUDE, new XMLSyntaxRule[]{
                    new ElementRule(TaxonSet.class, 1, Integer.MAX_VALUE)
            }, "One or more subsets of taxa which should be included from calculate the likelihood (the remaining taxa are excluded)", true),

            new ElementRule(EXCLUDE, new XMLSyntaxRule[]{
                    new ElementRule(TaxonSet.class, 1, Integer.MAX_VALUE)
            }, "One or more subsets of taxa which should be excluded from calculate the likelihood (which is calculated on the remaining subtree)", true),

            new ElementRule(CALIBRATION, calibration, true),
    };

}
