package com.tregouet.occam.data.problem_space.metrics;

public interface ISimilarityMetrics {

	double[][] getSimilarityMatrix();

	String[][] getReferenceMatrix();

	double[][] getAsymmetricalSimilarityMatrix();

	double[][] getDifferenceMatrix();

	double[] getTypicalityVector();

}