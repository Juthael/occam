package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.symmetrical_sim;

import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.symmetrical_sim.impl.MaximalRelativeSimilarity;

public class SimilarityMatrixBuilderFactory {
	
	public static final SimilarityMatrixBuilderFactory INSTANCE = new SimilarityMatrixBuilderFactory();
	
	private SimilarityMatrixBuilderFactory() {
	}
	
	public SimilarityMatrixBuilder apply(SimilarityMatrixBuilderStrategy strategy) {
		switch (strategy) {
		case MAXIMAL_RELATIVE_SIMILARITY : 
			return new MaximalRelativeSimilarity();
		default : 
			return null;
		}
	}

}
