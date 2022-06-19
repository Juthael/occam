package com.tregouet.occam.data.problem_space.metrics;

public interface ISimilarityMetrics {
	
	double[][] getSimilarityMatrix();
	
	double[][] getAsymmetricalSimilarityMatrix();
	
	double[][] getDifferenceMatrix();
	
	double[] getTypicalityVector();

}
