package com.tregouet.occam.data.representations.descriptions.metrics;

public interface ISimilarityMetrics {
	
	double[][] getSimilarityMatrix();
	
	double[][] getAsymmetricalSimilarityMatrix();
	
	double[] getTypicalityVector();
	
	int[] getParticularIDs();

}
