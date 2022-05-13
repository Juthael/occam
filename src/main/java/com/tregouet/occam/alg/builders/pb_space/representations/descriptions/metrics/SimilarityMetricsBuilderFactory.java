package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics;

import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.impl.DeferredMatricesInstantiation;

public class SimilarityMetricsBuilderFactory {

	public static final SimilarityMetricsBuilderFactory INSTANCE = new SimilarityMetricsBuilderFactory();

	private SimilarityMetricsBuilderFactory() {
	}

	public SimilarityMetricsBuilder apply(SimilarityMetricsBuilderStrategy strategy) {
		switch (strategy) {
		case DEFERRED_MATRICES_INSTANTIATION:
			return DeferredMatricesInstantiation.INSTANCE;
		default:
			return null;
		}
	}

}
