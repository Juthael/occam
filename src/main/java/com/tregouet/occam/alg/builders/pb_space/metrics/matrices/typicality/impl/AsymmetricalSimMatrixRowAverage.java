package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.typicality.impl;

import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.typicality.TypicalityVectorBuilder;

public class AsymmetricalSimMatrixRowAverage implements TypicalityVectorBuilder {

	public static final AsymmetricalSimMatrixRowAverage INSTANCE = new AsymmetricalSimMatrixRowAverage();

	private AsymmetricalSimMatrixRowAverage() {
	}

	@Override
	public double[] getTypicalityVector(double[][] asymmetricalSimilarityMatrix) {
		int nbOfParticulars = asymmetricalSimilarityMatrix.length;
		double[] typicalityVector = new double[nbOfParticulars];
		for (int i = 0 ; i < nbOfParticulars ; i++) {
			double sum = 0.0;
			for (int j = 0 ; j < nbOfParticulars ; j++)
				sum += asymmetricalSimilarityMatrix[j][i];
			typicalityVector[i] = sum / (nbOfParticulars);
		}
		return typicalityVector;
	}

}
