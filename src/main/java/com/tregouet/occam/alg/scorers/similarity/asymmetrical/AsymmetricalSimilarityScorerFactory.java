package com.tregouet.occam.alg.scorers.similarity.asymmetrical;

import com.tregouet.occam.alg.scorers.similarity.asymmetrical.impl.PortionOfTotalSimilarity;

public class AsymmetricalSimilarityScorerFactory {
	
	public static final AsymmetricalSimilarityScorerFactory INSTANCE = new AsymmetricalSimilarityScorerFactory();
	
	private AsymmetricalSimilarityScorerFactory() {
	}
	
	public AsymmetricalSimilarityScorer apply(AsymmetricalSimilarityScorerStrategy strategy) {
		switch (strategy) {
		case PORTION_OF_SIMILARITY : 
			return PortionOfTotalSimilarity.INSTANCE;
		default : 
			return null;
		}
	}

}
