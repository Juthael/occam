package com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.asymetrical_sim;

import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.asymetrical_sim.impl.SimilarityDivByMaxSimilarityDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.asymetrical_sim.impl.SimilarityDivByTotalSimilarityDEP;

public class AsymmetricalSimilarityMatrixBuilderFactoryDEP {

	public static final AsymmetricalSimilarityMatrixBuilderFactoryDEP INSTANCE =
			new AsymmetricalSimilarityMatrixBuilderFactoryDEP();

	private AsymmetricalSimilarityMatrixBuilderFactoryDEP() {
	}

	public AsymmetricalSimilarityMatrixBuilderDEP apply (AsymmetricalSimilarityMatrixBuilderStrategyDEP strategy) {
		switch (strategy) {
		case SIM_DIV_BY_MAX_SIM :
			return SimilarityDivByMaxSimilarityDEP.INSTANCE;
		case SIM_DIV_BY_TOTAL_SIM :
			return SimilarityDivByTotalSimilarityDEP.INSTANCE;
		default :
			return null;
		}
	}

}
