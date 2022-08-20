package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim.impl;

import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim.AsymmetricalSimilarityMatrixBuilder;

public class SimilarityDivByTotalSimilarity implements AsymmetricalSimilarityMatrixBuilder {

	public static final SimilarityDivByTotalSimilarity INSTANCE = new SimilarityDivByTotalSimilarity();

	private SimilarityDivByTotalSimilarity() {
	}

	@Override
	public double[][] getAsymmetricalSimilarityMatrix(double[][] similarityMatrix) {
		int size = similarityMatrix.length;
		double[][] asymmetricalSimMatrix = new double[size][size];
		for (int i = 0 ; i < size ; i++) {
			double iSimTotalValue = 0;
			for (int j = 0 ; j < size ; j++) {
				iSimTotalValue += similarityMatrix[i][j];
			}
			for (int j = 0 ; j < size ; j++) {
				asymmetricalSimMatrix[i][j] = similarityMatrix[i][j] / iSimTotalValue;
			}
		}
		return asymmetricalSimMatrix;
	}

}
