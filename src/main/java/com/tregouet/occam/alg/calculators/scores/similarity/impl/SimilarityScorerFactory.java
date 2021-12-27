package com.tregouet.occam.alg.calculators.scores.similarity.impl;

import java.util.function.Function;

import com.tregouet.occam.alg.calculators.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.alg.calculators.scores.similarity.SimilarityScoringStrategy;

public class SimilarityScorerFactory implements Function<SimilarityScoringStrategy, ISimilarityScorer> {
	
	public static final SimilarityScorerFactory INSTANCE = new SimilarityScorerFactory();
	
	private SimilarityScorerFactory() {
	}

	@Override
	public ISimilarityScorer apply(SimilarityScoringStrategy strategy) {
		switch (strategy) {
			default : 
				return null;
		}
	}

}
