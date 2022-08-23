package com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.asymetrical_sim.impl;

import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.asymetrical_sim.AsymmetricalSimilarityMatrixBuilderDEP;

public class SimilarityDivByMaxSimilarityDEP implements AsymmetricalSimilarityMatrixBuilderDEP {

	public static final SimilarityDivByMaxSimilarityDEP INSTANCE = new SimilarityDivByMaxSimilarityDEP();

	private SimilarityDivByMaxSimilarityDEP() {
	}

	@Override
	public double[][] getAsymmetricalSimilarityMatrix(double[][] similarityMatrix) {
		int size = similarityMatrix.length;
		double[][] asymmetricalSimMatrix = new double[size][size];
		for (int i = 0 ; i < size ; i++) {
			double iMaxSimValue = 0;
			for (int j = 0 ; j < size ; j++) {
				if (i != j) {
					double ijSimilarity = similarityMatrix[i][j];
					if (ijSimilarity > iMaxSimValue)
						iMaxSimValue = ijSimilarity;
				}
			}
			for (int j = 0 ; j < size ; j++) {
				if (i == j) {
					asymmetricalSimMatrix[i][j] = 0.0;
				}
				else asymmetricalSimMatrix[i][j] = similarityMatrix[i][j] / iMaxSimValue;
			}
		}
		return asymmetricalSimMatrix;
	}

}
