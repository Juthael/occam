package com.tregouet.occam.data.modules.similarity.metrics;

public interface ISimilarityMetrics {

	Double[][] getAsymmetricalSimilarityMatrix();

	Double[][] getDifferenceMatrix();

	Double[][] getSimilarityMatrix();

	double[] getTypicalityVector();

}