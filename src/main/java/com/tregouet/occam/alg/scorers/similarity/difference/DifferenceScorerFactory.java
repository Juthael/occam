package com.tregouet.occam.alg.scorers.similarity.difference;

import com.tregouet.occam.alg.scorers.similarity.difference.impl.SumOfDifferentiaeWeights;

public class DifferenceScorerFactory {
	
	public static final DifferenceScorerFactory INSTANCE = new DifferenceScorerFactory();
	
	private DifferenceScorerFactory() {
	}
	
	public DifferenceScorer apply(DifferenceScorerStrategy strategy) {
		switch(strategy) {
		case SUM_OF_DIFFERENTIAE_WEIGHTS : 
			return SumOfDifferentiaeWeights.INSTANCE;
		default : 
			return null;
		}
	}

}
