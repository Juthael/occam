package com.tregouet.occam.data.representations.descriptions.metrics;

public interface ISimilarityMetrics {

	double[][] getAsymmetricalSimilarityMatrix();

	int[] getParticularIDs();

	double[][] getSimilarityMatrix();

	double[] getTypicalityVector();

}
