package com.tregouet.occam.alg.scorers.similarity_DEP;

import com.tregouet.occam.alg.scorers.similarity_DEP.impl.DynamicFramingForPairsDEP;
import com.tregouet.occam.alg.scorers.similarity_DEP.impl.DynamicFramingForPairsNoCoeffDEP;
import com.tregouet.occam.alg.scorers.similarity_DEP.impl.DynamicFramingForSubsetsDEP;
import com.tregouet.occam.alg.scorers.similarity_DEP.impl.RelativeAsymmetricalDynamicFramingDEP;

public class RelativeSimilarityScorerFactoryDEP {

	public static final RelativeSimilarityScorerFactoryDEP INSTANCE = new RelativeSimilarityScorerFactoryDEP();

	private RelativeSimilarityScorerFactoryDEP() {
	}

	public RelativeAsymmetricalSimilarityScorerDEP getAsymmetricalSimilarityScorer(RelativeSimilarityScorerStrategyDEP strategy) {
		switch (strategy) {
		case DYNAMIC_FRAMING:
		case DYNAMIC_FRAMING_NO_COEFF :
			return new RelativeAsymmetricalDynamicFramingDEP();
		default:
			return null;
		}
	}

	public RelativeSubsetSimilarityScorerDEP getBasicSimilarityScorer(RelativeSimilarityScorerStrategyDEP strategy) {
		switch (strategy) {
		case DYNAMIC_FRAMING:
		case DYNAMIC_FRAMING_NO_COEFF :
			return new DynamicFramingForSubsetsDEP();
		default:
			return null;
		}
	}

	public RelativePairSimilarityScorerDEP getPairSimilarityScorer(RelativeSimilarityScorerStrategyDEP strategy) {
		switch (strategy) {
		case DYNAMIC_FRAMING:
			return new DynamicFramingForPairsDEP();
		case DYNAMIC_FRAMING_NO_COEFF :
			return new DynamicFramingForPairsNoCoeffDEP();
		default:
			return null;
		}
	}

}
