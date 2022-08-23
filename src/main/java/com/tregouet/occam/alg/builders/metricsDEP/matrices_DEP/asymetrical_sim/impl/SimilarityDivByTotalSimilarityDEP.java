package com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.asymetrical_sim.impl;

import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.asymetrical_sim.AsymmetricalSimilarityMatrixBuilderDEP;

public class SimilarityDivByTotalSimilarityDEP implements AsymmetricalSimilarityMatrixBuilderDEP {

	public static final SimilarityDivByTotalSimilarityDEP INSTANCE = new SimilarityDivByTotalSimilarityDEP();

	private SimilarityDivByTotalSimilarityDEP() {
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
