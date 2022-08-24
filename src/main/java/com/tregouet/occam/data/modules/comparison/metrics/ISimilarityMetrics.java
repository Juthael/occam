package com.tregouet.occam.data.modules.comparison.metrics;

public interface ISimilarityMetrics {

	Double[][] getAsymmetricalSimilarityMatrix();

	Double[][] getDifferenceMatrix();

	Double[][] getSimilarityMatrix();

	double[] getTypicalityVector();

}