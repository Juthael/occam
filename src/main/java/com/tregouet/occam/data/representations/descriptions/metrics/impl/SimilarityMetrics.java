package com.tregouet.occam.data.representations.descriptions.metrics.impl;

import com.tregouet.occam.alg.scorers.similarity.AsymmetricalSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.PairSimilarityScorer;
import com.tregouet.occam.data.representations.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.descriptions.subsets.IConceptPairIDs;
import com.tregouet.occam.data.representations.descriptions.subsets.impl.ConceptPairIDs;

public class SimilarityMetrics implements ISimilarityMetrics {

	private int[] particularIDs = null;
	private double[][] similarityMatrix = null;
	private double[][] asymmetricalSimilarityMatrix = null;
	private double[] typicalityVector = null;
	private PairSimilarityScorer pairSimilarityScorer = null;
	private AsymmetricalSimilarityScorer asymmetricalSimilarityScorer = null;
	
	SimilarityMetrics(int[] particularIDs, PairSimilarityScorer pairSimilarityScorer, 
			AsymmetricalSimilarityScorer asymmetricalSimilarityScorer){
		this.particularIDs = particularIDs;
		this.pairSimilarityScorer = pairSimilarityScorer;
		this.asymmetricalSimilarityScorer = asymmetricalSimilarityScorer;
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
	public double[][] getAsymmetricalSimilarityMatrix() {
		if (asymmetricalSimilarityMatrix == null)
			instantiateAsymmetricalSimilarityMatrix();
		return asymmetricalSimilarityMatrix;
	}

	@Override
	public double[] getTypicalityVector() {
		return typicalityVector;
	}
	
	private void instantiateSimilarityMatrix() {
		int nbOfParticulars = particularIDs.length;
		similarityMatrix = new double[nbOfParticulars][nbOfParticulars];
		for (int i = 0 ; i < nbOfParticulars ; i++) {
			for (int j = i ; j < nbOfParticulars ; j++) {
				int iParticularID = particularIDs[i];
				int jParticularID = particularIDs[j];
				IConceptPairIDs pair = new ConceptPairIDs(iParticularID, jParticularID);
				double similarityScore = pairSimilarityScorer.apply(pair).value();
				similarityMatrix[i][j] = similarityScore;
				if (i != j)
					similarityMatrix[j][i] = similarityScore;
			}
		}
	}
	
	private void instantiateAsymmetricalSimilarityMatrix() {
		int nbOfParticulars = particularIDs.length;
		asymmetricalSimilarityMatrix = new double[nbOfParticulars][nbOfParticulars];
		for (int i = 0 ; i < nbOfParticulars ; i++) {
			for (int j = 0 ; j < nbOfParticulars ; j++) {
				int iParticularID = particularIDs[i];
				int jParticularID = particularIDs[j];
				IConceptPairIDs pair = new ConceptPairIDs(iParticularID, jParticularID);
				asymmetricalSimilarityMatrix[i][j] = asymmetricalSimilarityScorer.apply(pair).value();
			}
		}
	}
	
	private void instantiateTypicalityVector() {
		//HERE
	}

}
