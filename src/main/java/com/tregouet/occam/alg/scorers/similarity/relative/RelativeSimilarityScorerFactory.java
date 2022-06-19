package com.tregouet.occam.alg.scorers.similarity.relative;

import com.tregouet.occam.alg.scorers.similarity.relative.impl.RelativeAsymmetricalDynamicFraming;
import com.tregouet.occam.alg.scorers.similarity.relative.impl.DynamicFramingForPairs;
import com.tregouet.occam.alg.scorers.similarity.relative.impl.DynamicFramingForSubsets;

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
