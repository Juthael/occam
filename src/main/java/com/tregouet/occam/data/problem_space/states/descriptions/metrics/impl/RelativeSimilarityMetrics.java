package com.tregouet.occam.data.problem_space.states.descriptions.metrics.impl;

import com.tregouet.occam.alg.scorers.similarity.RelativeAsymmetricalSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.RelativePairSimilarityScorer;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.IRelativeSimilarityMetrics;

public class RelativeSimilarityMetrics implements IRelativeSimilarityMetrics {

	private int[] particularIDs = null;
	private double[][] similarityMatrix = null;
	private double[][] asymmetricalSimilarityMatrix = null;
	private double[] typicalityVector = null;
	private RelativePairSimilarityScorer relativePairSimilarityScorer = null;
	private RelativeAsymmetricalSimilarityScorer relativeAsymmetricalSimilarityScorer = null;

	public RelativeSimilarityMetrics(int[] particularIDs, RelativePairSimilarityScorer relativePairSimilarityScorer,
			RelativeAsymmetricalSimilarityScorer relativeAsymmetricalSimilarityScorer) {
		this.particularIDs = particularIDs;
		this.relativePairSimilarityScorer = relativePairSimilarityScorer;
		this.relativeAsymmetricalSimilarityScorer = relativeAsymmetricalSimilarityScorer;
	}

	@Override
	public double[][] getAsymmetricalSimilarityMatrix() {
		if (asymmetricalSimilarityMatrix == null)
			instantiateAsymmetricalSimilarityMatrix();
		return asymmetricalSimilarityMatrix;
	}

	@Override
	public int[] getParticularIDs() {
		return particularIDs;
	}

	@Override
	public double[][] getSimilarityMatrix() {
		if (similarityMatrix == null)
			instantiateSimilarityMatrix();
		return similarityMatrix;
	}

	@Override
	public double[] getTypicalityVector() {
		if (typicalityVector == null)
			instantiateTypicalityVector();
		return typicalityVector;
	}

	private void instantiateAsymmetricalSimilarityMatrix() {
		int nbOfParticulars = particularIDs.length;
		asymmetricalSimilarityMatrix = new double[nbOfParticulars][nbOfParticulars];
		for (int i = 0; i < nbOfParticulars; i++) {
			for (int j = 0; j < nbOfParticulars; j++) {
				int iParticularID = particularIDs[i];
				int jParticularID = particularIDs[j];
				asymmetricalSimilarityMatrix[i][j] = relativeAsymmetricalSimilarityScorer.score(iParticularID, jParticularID);
			}
		}
	}

	private void instantiateSimilarityMatrix() {
		int nbOfParticulars = particularIDs.length;
		similarityMatrix = new double[nbOfParticulars][nbOfParticulars];
		for (int i = 0; i < nbOfParticulars; i++) {
			for (int j = i; j < nbOfParticulars; j++) {
				int iParticularID = particularIDs[i];
				int jParticularID = particularIDs[j];
				double similarityScore = relativePairSimilarityScorer.score(iParticularID, jParticularID);
				similarityMatrix[i][j] = similarityScore;
				if (i != j)
					similarityMatrix[j][i] = similarityScore;
			}
		}
	}

	private void instantiateTypicalityVector() {
		int nbOfParticulars = particularIDs.length;
		typicalityVector = new double[nbOfParticulars];
		if (similarityMatrix == null)
			instantiateSimilarityMatrix();
		for (int i = 0; i < nbOfParticulars; i++) {
			double typicalityScore = 0.0;
			for (int j = 0; j < nbOfParticulars; j++) {
				typicalityScore += similarityMatrix[i][j];
			}
			typicalityScore = typicalityScore / nbOfParticulars;
			typicalityVector[i] = typicalityScore;
		}
	}

}
