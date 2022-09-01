package com.tregouet.occam.alg.scorers.comparisons.difference;

import com.tregouet.occam.alg.scorers.comparisons.difference.impl.DifferentiaeWeights;

public class DifferenceScorerFactory {

	public static final DifferenceScorerFactory INSTANCE = new DifferenceScorerFactory();

	private DifferenceScorerFactory() {
	}

	public DifferenceScorer apply(DifferenceScorerStrategy strategy) {
		switch(strategy) {
		case DIFFERENTIAE_WEIGHTS :
			return DifferentiaeWeights.INSTANCE;
		default :
			return null;
		}
	}

}
