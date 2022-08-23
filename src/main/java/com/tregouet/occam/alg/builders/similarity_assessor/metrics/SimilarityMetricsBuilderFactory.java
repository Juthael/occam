package com.tregouet.occam.alg.builders.similarity_assessor.metrics;

import com.tregouet.occam.alg.builders.similarity_assessor.metrics.impl.SimilaritiesThenDifferences;

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
