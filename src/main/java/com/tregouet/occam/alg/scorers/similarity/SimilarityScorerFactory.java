package com.tregouet.occam.alg.scorers.similarity;

import com.tregouet.occam.alg.scorers.similarity.impl.AsymmetricalDynamicFraming;
import com.tregouet.occam.alg.scorers.similarity.impl.DynamicFramingForPairs;
import com.tregouet.occam.alg.scorers.similarity.impl.DynamicFramingForSubsets;

public class SimilarityScorerFactory {

	public static final SimilarityScorerFactory INSTANCE = new SimilarityScorerFactory();

	private SimilarityScorerFactory() {
	}

	public AsymmetricalSimilarityScorer getAsymmetricalSimilarityScorer(SimilarityScorerStrategy strategy) {
		switch (strategy) {
			case DYNAMIC_FRAMING :
				return new AsymmetricalDynamicFraming();
			default :
				return null;
		}
	}

	public SubsetSimilarityScorer getBasicSimilarityScorer(SimilarityScorerStrategy strategy) {
		switch (strategy) {
			case DYNAMIC_FRAMING :
				return new DynamicFramingForSubsets();
			default :
				return null;
		}
	}

	public PairSimilarityScorer getPairSimilarityScorer(SimilarityScorerStrategy strategy) {
		switch (strategy) {
			case DYNAMIC_FRAMING :
				return new DynamicFramingForPairs();
			default :
				return null;
		}
	}

}
