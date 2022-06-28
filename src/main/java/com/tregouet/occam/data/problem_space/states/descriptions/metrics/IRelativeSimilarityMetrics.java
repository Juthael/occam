package com.tregouet.occam.data.problem_space.states.descriptions.metrics;

public interface IRelativeSimilarityMetrics {

	double[][] getAsymmetricalSimilarityMatrix();

	int[] getParticularIDs();

	double[][] getSimilarityMatrix();

	double[] getTypicalityVector();

}
