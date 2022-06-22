package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.symmetrical_sim.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.symmetrical_sim.SimilarityMatrixBuilder;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public class MaximalRelativeSimilarity implements SimilarityMatrixBuilder {
	
	private double[][] similarityMatrix = null;
	private String[][] referenceMatrix = null;
	
	public MaximalRelativeSimilarity() {
	}

	@Override
	public SimilarityMatrixBuilder setUp(int nbOfParticulars,
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		similarityMatrix = new double[nbOfParticulars][nbOfParticulars];
		referenceMatrix = initializeReferenceMatrix(nbOfParticulars);
		for (IRepresentation representation : problemGraph.vertexSet()) {
			int representationID = representation.iD();
			double[][] relativeSimilarityMatrix = representation.getSimilarityMatrix();
			for (int i = 0 ; i < nbOfParticulars ; i++) {
				for (int j = 0 ; j < nbOfParticulars ; j++) {
					double absoluteSimilarity = similarityMatrix[i][j];
					double relativeSimilarity = relativeSimilarityMatrix[i][j];
					int comparison = Double.compare(relativeSimilarity, absoluteSimilarity);
					if (comparison == 0)
						updateReferenceMatrix(i, j, representationID, false);
					else if (comparison > 0) {
						similarityMatrix[i][j] = relativeSimilarity;
						updateReferenceMatrix(i, j, representationID, true);
					}
				}
			}
		}
		return this;
	}

	@Override
	public double[][] getSimilarityMatrix() {
		return similarityMatrix;
	}

	@Override
	public String[][] getReferenceMatrix() {
		return referenceMatrix;
	}
	
	private static String[][] initializeReferenceMatrix(int size) {
		String[][] referenceMatrix = new String[size][size];
		for (int i = 0 ; i < size ; i++) {
			for (int j = 0 ; j < size ; j++)
				referenceMatrix[i][j] = new String();
		}
		return referenceMatrix;
	}
	
	private void updateReferenceMatrix(int i, int j, int referenceID, boolean resetPrevious) {
		String target = referenceMatrix[i][j];
		if (!resetPrevious && !target.isEmpty())
			target += ", " + Integer.toString(referenceID);
		else target = Integer.toString(referenceID);
	}
	
}
