package com.tregouet.occam.alg.scorers.similarity;

import com.tregouet.occam.alg.scorers.similarity.impl.AsymmetricalDynamicFraming;
import com.tregouet.occam.alg.scorers.similarity.impl.DynamicFraming;

public class SimilarityScorerFactory {
	
	public static final SimilarityScorerFactory INSTANCE = new SimilarityScorerFactory();
	
	private SimilarityScorerFactory() {
	}
	
	public BasicSimilarityScorer getBasicSimilarityScorer(SimilarityScorerStrategy strategy) {
		switch (strategy) {
			case DYNAMIC_FRAMING : 
				return new DynamicFraming();
			default : 
				return null;
		}
	}
	
	public AsymmetricalSimilarityScorer getAsymmetricalSimilarityScorer(SimilarityScorerStrategy strategy) {
		switch (strategy) {
			case DYNAMIC_FRAMING : 
				return new AsymmetricalDynamicFraming();
			default : 
				return null;
		}
	}

}
