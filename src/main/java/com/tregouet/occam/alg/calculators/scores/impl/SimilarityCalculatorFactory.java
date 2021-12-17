package com.tregouet.occam.alg.calculators.scores.impl;

import java.util.function.Function;

import com.tregouet.occam.alg.calculators.scores.ISimilarityScorer;
import com.tregouet.occam.alg.calculators.scores.SimilarityScoringStrategy;

public class SimilarityCalculatorFactory implements Function<SimilarityScoringStrategy, ISimilarityScorer> {
	
	public static final SimilarityCalculatorFactory INSTANCE = new SimilarityCalculatorFactory();
	
	private SimilarityCalculatorFactory() {
	}

	@Override
	public ISimilarityScorer apply(SimilarityScoringStrategy strategy) {
		switch (strategy) {
			default : 
				return null;
		}
	}

}
