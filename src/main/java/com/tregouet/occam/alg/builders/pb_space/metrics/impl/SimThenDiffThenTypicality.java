package com.tregouet.occam.alg.builders.pb_space.metrics.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.symmetrical_sim.SimilarityMatrixBuilder;
import com.tregouet.occam.data.problem_space.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.problem_space.metrics.impl.SimilarityMetrics;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public class SimThenDiffThenTypicality implements SimilarityMetricsBuilder {

	public static final SimThenDiffThenTypicality INSTANCE = new SimThenDiffThenTypicality();

	private SimThenDiffThenTypicality() {
	}

	@Override
	public ISimilarityMetrics apply(IConceptLattice lattice, DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> pbGraph, 
			double[][] differenceMatrixParam) {
		double[][] similarityMatrix;
		String[][] referenceMatrix;
		double[][] asymmetricalSimilarityMatrix;
		double[][] differenceMatrix;
		double[] typicalityVector;
		SimilarityMatrixBuilder simMatrixBuilder =
				BuildersAbstractFactory.INSTANCE.getSimilarityMatrixBuilder().setUp(lattice.getContextObjects().size(), pbGraph);
		similarityMatrix = simMatrixBuilder.getSimilarityMatrix();
		referenceMatrix = simMatrixBuilder.getReferenceMatrix();
		asymmetricalSimilarityMatrix = BuildersAbstractFactory.INSTANCE
				.getAsymmetricalSimilarityMatrixBuilder().getAsymmetricalSimilarityMatrix(similarityMatrix);
		if (differenceMatrixParam == null) {
			differenceMatrix = BuildersAbstractFactory.INSTANCE.getDifferenceMatrixBuilder().getDifferenceMatrix(lattice);
		}
		else differenceMatrix = differenceMatrixParam;
		typicalityVector = BuildersAbstractFactory.INSTANCE
				.getTypicalityVectorBuilder().getTypicalityVector(asymmetricalSimilarityMatrix);
		return new SimilarityMetrics(
				similarityMatrix, referenceMatrix, asymmetricalSimilarityMatrix, differenceMatrix, typicalityVector);
	}

}
