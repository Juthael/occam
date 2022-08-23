package com.tregouet.occam.alg.builders.metricsDEP;

import com.tregouet.occam.alg.builders.metricsDEP.impl.SimThenDiffThenTypicalityDEP;

public class SimilarityMetricsBuilderFactoryDEP {

	public static final SimilarityMetricsBuilderFactoryDEP INSTANCE = new SimilarityMetricsBuilderFactoryDEP();

	private SimilarityMetricsBuilderFactoryDEP() {
	}

	public SimilarityMetricsBuilderDEP apply(SimilarityMetricsBuilderStrategyDEP strategy) {
		switch (strategy) {
		case SIM_THEN_DIFF_THEN_TYPICALITY :
			return SimThenDiffThenTypicalityDEP.INSTANCE;
		default :
			return null;
		}
	}

}
