package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics;

import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.impl.ReplaceMissingParticularsByMostSpecificConcept;

public class RelativeSimilarityMetricsBuilderFactory {

	public static final RelativeSimilarityMetricsBuilderFactory INSTANCE = new RelativeSimilarityMetricsBuilderFactory();

	private RelativeSimilarityMetricsBuilderFactory() {
	}

	public RelativeSimilarityMetricsBuilder apply(RelativeSimilarityMetricsBuilderStrategy strategy) {
		switch (strategy) {
		case MOST_SPECIFIC_CONCEPT :
			return ReplaceMissingParticularsByMostSpecificConcept.INSTANCE;
		default :
			return null;
		}
	}

}
