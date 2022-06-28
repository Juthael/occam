package com.tregouet.occam.data.problem_space.metrics;

public interface ISimilarityMetrics {

	double[][] getAsymmetricalSimilarityMatrix();

	double[][] getDifferenceMatrix();

	String[][] getReferenceMatrix();

	double[][] getSimilarityMatrix();

	double[] getTypicalityVector();

}