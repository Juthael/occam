package com.tregouet.occam.alg.builders.problem_spaces.partial_representations.metrics;

import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.metrics.impl.ReplaceMissingParticularsByMostSpecificGenus;

public class SimilarityPartialMetricsBuilderFactory {
	
	public static final SimilarityPartialMetricsBuilderFactory INSTANCE = new SimilarityPartialMetricsBuilderFactory();
	
	private SimilarityPartialMetricsBuilderFactory() {
	}
	
	public SimilarityPartialMetricsBuilder apply(SimilarityPartialMetricsBuilderStrategy strategy) {
		switch (strategy) {
		case MOST_SPECIFIC_GENUS : 
			return ReplaceMissingParticularsByMostSpecificGenus.INSTANCE;
		default : 
			return null;
		}
	}

}
