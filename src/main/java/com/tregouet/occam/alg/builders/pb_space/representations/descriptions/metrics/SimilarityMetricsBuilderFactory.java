package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics;

import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.impl.ReplaceMissingParticularsByMostSpecificConcept;

public class SimilarityMetricsBuilderFactory {

	public static final SimilarityMetricsBuilderFactory INSTANCE = new SimilarityMetricsBuilderFactory();

	private SimilarityMetricsBuilderFactory() {
	}

	public SimilarityMetricsBuilder apply(SimilarityMetricsBuilderStrategy strategy) {
		switch (strategy) {
		case MOST_SPECIFIC_CONCEPT :
			return ReplaceMissingParticularsByMostSpecificConcept.INSTANCE;
		default :
			return null;
		}
	}

}
