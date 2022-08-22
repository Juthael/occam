package com.tregouet.occam.alg.builders.metrics.matrices.symmetrical_sim.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.metrics.matrices.symmetrical_sim.SimilarityMatrixBuilder;
import com.tregouet.occam.alg.builders.metrics.matrices.symmetrical_sim.impl.utils.References;
import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;

public class MaximalRelativeSimilarity implements SimilarityMatrixBuilder {

	private double[][] similarityMatrix = null;
	private References[][] referenceMatrix = null;
	private DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph = null;

	public MaximalRelativeSimilarity() {
	}

	@Override
	public String[][] getReferenceMatrix() {
		int size = referenceMatrix.length;
		String[][] refStringMatrix = new String[size][size];
		for (int i = 0 ; i < size ; i++) {
			for (int j = 0 ; j <= i ; j++) {
				String refAsString = referenceMatrix[i][j].toString();
				refStringMatrix[i][j] = refAsString;
				if (i != j)
					refStringMatrix[j][i] = refAsString;
			}
		}
		return refStringMatrix;
	}

	@Override
	public double[][] getSimilarityMatrix() {
		return similarityMatrix;
	}

	@Override
	public SimilarityMatrixBuilder setUp(int nbOfParticulars,
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		this.problemGraph = problemGraph;
		similarityMatrix = new double[nbOfParticulars][nbOfParticulars];
		referenceMatrix = initializeReferenceMatrix(nbOfParticulars);
		for (IRepresentation representation : problemGraph.vertexSet()) {
			double[][] relativeSimilarityMatrix = representation.getSimilarityMatrix();
			for (int i = 0 ; i < nbOfParticulars ; i++) {
				for (int j = 0 ; j <= i ; j++) {
					double absoluteSimilarity = similarityMatrix[i][j];
					double relativeSimilarity = relativeSimilarityMatrix[i][j];
					int comparison = Double.compare(relativeSimilarity, absoluteSimilarity);
					if (comparison == 0) {
						referenceMatrix[i][j].addNew(representation);
					}
					else if (comparison > 0) {
						similarityMatrix[i][j] = relativeSimilarity;
						similarityMatrix[j][i] = relativeSimilarity;
						referenceMatrix[i][j].clearPreviousAddNew(representation);
					}
				}
			}
		}
		return this;
	}

	private References[][] initializeReferenceMatrix(int size) {
		References[][] referenceMatrix = new References[size][];
		for (int i = 0 ; i < size ; i++) {
			referenceMatrix[i] = new References[i+1];
		}
		for (int i = 0 ; i < size ; i++) {
			for (int j = 0 ; j <= i ; j++)
				referenceMatrix[i][j] = new References(problemGraph);
		}
		return referenceMatrix;
	}

}
