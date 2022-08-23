package com.tregouet.occam.data.modules.similarity.metrics.impl;

import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;

public class SimilarityMetrics implements ISimilarityMetrics {

	private final Double[][] similarityMatrix;
	private final Double[][] asymmetricalSimilarityMatrix;
	private final double[] typicalityVector;
	private final Double[][] differenceMatrix;

	public SimilarityMetrics(Double[][] similarityMatrix, Double[][] asymmetricalSimilarityMatrix, 
			double[] typicalityVector, Double[][] differenceMatrix) {
		this.similarityMatrix = similarityMatrix;
		this.asymmetricalSimilarityMatrix = asymmetricalSimilarityMatrix;
		this.typicalityVector = typicalityVector;
		this.differenceMatrix = differenceMatrix;
	}

	@Override
	public Double[][] getAsymmetricalSimilarityMatrix() {
		return asymmetricalSimilarityMatrix;
	}

	@Override
	public Double[][] getDifferenceMatrix() {
		return differenceMatrix;
	}

	@Override
	public Double[][] getSimilarityMatrix() {
		return similarityMatrix;
	}

	@Override
	public double[] getTypicalityVector() {
		return typicalityVector;
	}

}
