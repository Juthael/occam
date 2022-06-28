package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim.impl;

import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim.AsymmetricalSimilarityMatrixBuilder;

public class SimilarityDivByMaxSimilarity implements AsymmetricalSimilarityMatrixBuilder {

	public static final SimilarityDivByMaxSimilarity INSTANCE = new SimilarityDivByMaxSimilarity();

	private SimilarityDivByMaxSimilarity() {
	}

	@Override
	public double[][] getAsymmetricalSimilarityMatrix(double[][] similarityMatrix) {
		int size = similarityMatrix.length;
		double[][] asymmetricalSimMatrix = new double[size][size];
		for (int i = 0 ; i < size ; i++) {
			double iMaxSimValue = 0;
			for (int j = 0 ; j < size ; j++) {
				double ijSimilarity = similarityMatrix[i][j];
				if (ijSimilarity > iMaxSimValue)
					iMaxSimValue = ijSimilarity;
			}
			for (int j = 0 ; j < size ; j++)
				asymmetricalSimMatrix[i][j] = similarityMatrix[i][j] / iMaxSimValue;
		}
		return asymmetricalSimMatrix;
	}

}
