package com.tregouet.occam.data.representations.descriptions.metrics;

public interface IRelativeSimilarityMetrics {

	double[][] getAsymmetricalSimilarityMatrix();

	int[] getParticularIDs();

	double[][] getSimilarityMatrix();

	double[] getTypicalityVector();

}
