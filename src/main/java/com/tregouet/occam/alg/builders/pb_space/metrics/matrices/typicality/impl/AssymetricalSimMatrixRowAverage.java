package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.typicality.impl;

import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.typicality.TypicalityVectorBuilder;

public class AssymetricalSimMatrixRowAverage implements TypicalityVectorBuilder {
	
	public static final AssymetricalSimMatrixRowAverage INSTANCE = new AssymetricalSimMatrixRowAverage();
	
	private AssymetricalSimMatrixRowAverage() {
	}

	@Override
	public double[] getTypicalityVector(double[][] asymmetricalSimilarityMatrix) {
		int nbOfParticulars = asymmetricalSimilarityMatrix.length;
		double[] typicalityVector = new double[nbOfParticulars];
		for (int i = 0 ; i < nbOfParticulars ; i++) {
			double sum = 0.0;
			for (int j = 0 ; j < nbOfParticulars ; j++)
				sum += asymmetricalSimilarityMatrix[i][j];
			typicalityVector[i] = sum / ((double) nbOfParticulars);
		}
		return typicalityVector;
	}

}
