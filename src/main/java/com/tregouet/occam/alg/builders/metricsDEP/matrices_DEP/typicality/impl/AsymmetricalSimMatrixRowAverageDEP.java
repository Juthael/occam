package com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.typicality.impl;

import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.typicality.TypicalityVectorBuilderDEP;

public class AsymmetricalSimMatrixRowAverageDEP implements TypicalityVectorBuilderDEP {

	public static final AsymmetricalSimMatrixRowAverageDEP INSTANCE = new AsymmetricalSimMatrixRowAverageDEP();

	private AsymmetricalSimMatrixRowAverageDEP() {
	}

	@Override
	public double[] getTypicalityVector(double[][] asymmetricalSimilarityMatrix) {
		int nbOfParticulars = asymmetricalSimilarityMatrix.length;
		double[] typicalityVector = new double[nbOfParticulars];
		for (int i = 0 ; i < nbOfParticulars ; i++) {
			double sum = 0.0;
			for (int j = 0 ; j < nbOfParticulars ; j++) {
				if (i != j)
					sum += asymmetricalSimilarityMatrix[j][i];
			}

			typicalityVector[i] = sum / (nbOfParticulars - 1);
		}
		return typicalityVector;
	}

}
