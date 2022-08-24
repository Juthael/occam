package com.tregouet.occam.alg.scorers.comparisons.similarity.basic;

import com.tregouet.occam.alg.scorers.comparisons.similarity.basic.impl.CoeffFreeDifferentiaeWeightSum;
import com.tregouet.occam.alg.scorers.comparisons.similarity.basic.impl.DifferentiaeWeightSum;
import com.tregouet.occam.alg.scorers.comparisons.similarity.basic.impl.SpeciesWeight;

public class SimilarityScorerFactory {

	public static final SimilarityScorerFactory INSTANCE = new SimilarityScorerFactory();

	private SimilarityScorerFactory() {
	}

	public SimilarityScorer apply(SimilarityScorerStrategy strategy) {
		switch (strategy) {
		case COEFF_FREE_DIFFERENTIAE_WEIGHT_SUM :
			return CoeffFreeDifferentiaeWeightSum.INSTANCE;
		case DIFFERENTIAE_WEIGHT_SUM :
			return DifferentiaeWeightSum.INSTANCE;
		case SPECIES_WEIGHT :
			return SpeciesWeight.INSTANCE;
		default :
			return null;
		}
	}

}
