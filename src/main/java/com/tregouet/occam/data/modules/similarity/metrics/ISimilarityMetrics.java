package com.tregouet.occam.data.modules.similarity.metrics;

public interface ISimilarityMetrics {

	double[][] getAsymmetricalSimilarityMatrix();

	double[][] getDifferenceMatrix();

	String[][] getReferenceMatrix();

	double[][] getSimilarityMatrix();

	double[] getTypicalityVector();

}