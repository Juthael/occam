package com.tregouet.occam.alg.scorers.representations;

import com.tregouet.occam.alg.scorers.representations.impl.LecticComparisonOfRanks;

public class RepresentationScorerFactory {
	
	public static final RepresentationScorerFactory INSTANCE = new RepresentationScorerFactory();
	
	private RepresentationScorerFactory() {
	}
	
	public RepresentationLecticScorer apply(RepresentationScorerStrategy strategy) {
		switch (strategy) {
			case LEXICOGRAPHIC_COMPARISON_OF_RANKS : 
				return LecticComparisonOfRanks.INSTANCE;
			default : 
				return null;
		}
	}

}
