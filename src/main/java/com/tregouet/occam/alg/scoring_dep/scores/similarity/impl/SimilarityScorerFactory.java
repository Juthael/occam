package com.tregouet.occam.alg.scoring_dep.scores.similarity.impl;

import java.util.function.Function;

import com.tregouet.occam.alg.scoring_dep.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.alg.scoring_dep.scores.similarity.SimilarityScoringStrategy;

public class SimilarityScorerFactory implements Function<SimilarityScoringStrategy, ISimilarityScorer> {
	
	public static final SimilarityScorerFactory INSTANCE = new SimilarityScorerFactory();
	
	private SimilarityScorerFactory() {
	}

	@Override
	public ISimilarityScorer apply(SimilarityScoringStrategy strategy) {
		switch (strategy) {
			case DYNAMIC_FRAMING : 
				return new DynamicFraming();
			default : 
				return null;
		}
	}

}
