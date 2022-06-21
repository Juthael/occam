package com.tregouet.occam.data.problem_space.metrics.impl;

import com.tregouet.occam.data.problem_space.metrics.ISimilarityMetrics;

public class SimilarityMetrics implements ISimilarityMetrics {

	private final double[][] similarityMatrix;
	private final String[][] referenceMatrix;
	private final double[][] asymmetricalSimilarityMatrix;
	private final double[][] differenceMatrix;
	private final double[] typicalityVector;

	public SimilarityMetrics(double[][] similarityMatrix, String[][] referenceMatrix, 
			double[][] asymmetricalSimilarityMatrix, double[][] differenceMatrix, double[] typicalityVector) {
		this.similarityMatrix = similarityMatrix;
		this.referenceMatrix = referenceMatrix;
		this.asymmetricalSimilarityMatrix = asymmetricalSimilarityMatrix;
		this.differenceMatrix = differenceMatrix;
		this.typicalityVector = typicalityVector;
	}

	@Override
	public double[][] getSimilarityMatrix() {
		return similarityMatrix;
	}
	
	@Override
	public String[][] getReferenceMatrix() {
		return referenceMatrix;
	}

	@Override
	public double[][] getAsymmetricalSimilarityMatrix() {
		return asymmetricalSimilarityMatrix;
	}

	@Override
	public double[][] getDifferenceMatrix() {
		return differenceMatrix;
	}

	@Override
	public double[] getTypicalityVector() {
		return typicalityVector;
	}

}
