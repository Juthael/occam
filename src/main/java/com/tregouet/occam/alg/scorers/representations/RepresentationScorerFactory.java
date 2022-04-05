package com.tregouet.occam.alg.scorers.representations;

import com.tregouet.occam.alg.scorers.representations.impl.LexicographicComparisonOfRanks;

public class RepresentationScorerFactory {
	
	public static final RepresentationScorerFactory INSTANCE = new RepresentationScorerFactory();
	
	private RepresentationScorerFactory() {
	}
	
	public RepresentationLexicographicScorer apply(RepresentationScorerStrategy strategy) {
		switch (strategy) {
			case LEXICOGRAPHIC_COMPARISON_OF_RANKS : 
				return LexicographicComparisonOfRanks.INSTANCE;
			default : 
				return null;
		}
	}

}
