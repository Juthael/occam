package com.tregouet.occam.alg.scorers.similarity.symmetrical;

import com.tregouet.occam.alg.scorers.similarity.symmetrical.impl.CoeffFreeDifferentiaeWeightSum;
import com.tregouet.occam.alg.scorers.similarity.symmetrical.impl.DifferentiaeWeightSum;

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
		default : 
			return null;
		}
	}

}
