package com.tregouet.occam.alg.builders.pb_space.metrics;

import com.tregouet.occam.alg.builders.pb_space.metrics.impl.SimThenDiffThenTypicality;

public class SimilarityMetricsBuilderFactory {

	public static final SimilarityMetricsBuilderFactory INSTANCE = new SimilarityMetricsBuilderFactory();

	private SimilarityMetricsBuilderFactory() {
	}

	public SimilarityMetricsBuilder apply(SimilarityMetricsBuilderStrategy strategy) {
		switch (strategy) {
		case SIM_THEN_DIFF_THEN_TYPICALITY :
			return SimThenDiffThenTypicality.INSTANCE;
		default :
			return null;
		}
	}

}
