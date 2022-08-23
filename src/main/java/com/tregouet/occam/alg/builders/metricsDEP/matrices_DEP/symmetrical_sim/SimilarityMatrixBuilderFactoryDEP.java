package com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.symmetrical_sim;

import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.symmetrical_sim.impl.MaximalRelativeSimilarityDEP;

public class SimilarityMatrixBuilderFactoryDEP {

	public static final SimilarityMatrixBuilderFactoryDEP INSTANCE = new SimilarityMatrixBuilderFactoryDEP();

	private SimilarityMatrixBuilderFactoryDEP() {
	}

	public SimilarityMatrixBuilderDEP apply(SimilarityMatrixBuilderStrategyDEP strategy) {
		switch (strategy) {
		case MAXIMAL_RELATIVE_SIMILARITY :
			return new MaximalRelativeSimilarityDEP();
		default :
			return null;
		}
	}

}
