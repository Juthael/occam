package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim;

import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim.impl.SimilarityDivByMaxSimilarity;

public class AsymmetricalSimilarityMatrixBuilderFactory {

	public static final AsymmetricalSimilarityMatrixBuilderFactory INSTANCE =
			new AsymmetricalSimilarityMatrixBuilderFactory();

	private AsymmetricalSimilarityMatrixBuilderFactory() {
	}

	public AsymmetricalSimilarityMatrixBuilder apply (AsymmetricalSimilarityMatrixBuilderStrategy strategy) {
		switch (strategy) {
		case SIM_DIV_BY_MAX_SIM :
			return SimilarityDivByMaxSimilarity.INSTANCE;
		default :
			return null;
		}
	}

}
