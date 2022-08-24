package com.tregouet.occam.alg.builders.comparator.metrics;

import com.tregouet.occam.alg.builders.comparator.metrics.impl.SimilaritiesThenDifferences;

public class SimilarityMetricsBuilderFactory {

	public static final SimilarityMetricsBuilderFactory INSTANCE = new SimilarityMetricsBuilderFactory();

	private SimilarityMetricsBuilderFactory() {
	}

	public SimilarityMetricsBuilder apply(SimilarityMetricsBuilderStrategy strategy) {
		switch (strategy) {
		case SIM_THEN_DIFF :
			return SimilaritiesThenDifferences.INSTANCE;
		default :
			return null;
		}
	}

}
