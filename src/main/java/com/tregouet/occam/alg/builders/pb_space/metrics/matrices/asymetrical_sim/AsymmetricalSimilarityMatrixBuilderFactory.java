package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim;

import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim.impl.SimilarityDivByMaxSimilarity;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim.impl.SimilarityDivByTotalSimilarity;

public class AsymmetricalSimilarityMatrixBuilderFactory {

	public static final AsymmetricalSimilarityMatrixBuilderFactory INSTANCE =
			new AsymmetricalSimilarityMatrixBuilderFactory();

	private AsymmetricalSimilarityMatrixBuilderFactory() {
	}

	public AsymmetricalSimilarityMatrixBuilder apply (AsymmetricalSimilarityMatrixBuilderStrategy strategy) {
		switch (strategy) {
		case SIM_DIV_BY_MAX_SIM :
			return SimilarityDivByMaxSimilarity.INSTANCE;
		case SIM_DIV_BY_TOTAL_SIM :
			return SimilarityDivByTotalSimilarity.INSTANCE;
		default :
			return null;
		}
	}

}
