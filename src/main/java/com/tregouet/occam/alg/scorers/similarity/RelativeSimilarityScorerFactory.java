package com.tregouet.occam.alg.scorers.similarity;

import com.tregouet.occam.alg.scorers.similarity.impl.DynamicFramingForPairs;
import com.tregouet.occam.alg.scorers.similarity.impl.DynamicFramingForSubsets;
import com.tregouet.occam.alg.scorers.similarity.impl.RelativeAsymmetricalDynamicFraming;

public class RelativeSimilarityScorerFactory {

	public static final RelativeSimilarityScorerFactory INSTANCE = new RelativeSimilarityScorerFactory();

	private RelativeSimilarityScorerFactory() {
	}

	public RelativeAsymmetricalSimilarityScorer getAsymmetricalSimilarityScorer(RelativeSimilarityScorerStrategy strategy) {
		switch (strategy) {
		case DYNAMIC_FRAMING:
			return new RelativeAsymmetricalDynamicFraming();
		default:
			return null;
		}
	}

	public RelativeSubsetSimilarityScorer getBasicSimilarityScorer(RelativeSimilarityScorerStrategy strategy) {
		switch (strategy) {
		case DYNAMIC_FRAMING:
			return new DynamicFramingForSubsets();
		default:
			return null;
		}
	}

	public RelativePairSimilarityScorer getPairSimilarityScorer(RelativeSimilarityScorerStrategy strategy) {
		switch (strategy) {
		case DYNAMIC_FRAMING:
			return new DynamicFramingForPairs();
		default:
			return null;
		}
	}

}
