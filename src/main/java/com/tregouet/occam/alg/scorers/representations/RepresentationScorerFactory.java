package com.tregouet.occam.alg.scorers.representations;

import com.tregouet.occam.alg.scorers.representations.impl.LecticComparisonOfRanks;

public class RepresentationScorerFactory {

	public static final RepresentationScorerFactory INSTANCE = new RepresentationScorerFactory();

	private RepresentationScorerFactory() {
	}

	public RepresentationScorer apply(RepresentationScorerStrategy strategy) {
		switch (strategy) {
			case LECTIC_COMPARISON_OF_RANKS :
				return LecticComparisonOfRanks.INSTANCE;
			default :
				return null;
		}
	}

}
