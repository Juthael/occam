package com.tregouet.occam.alg.builders.metrics.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.alg.builders.metrics.matrices.symmetrical_sim.SimilarityMatrixBuilder;
import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.modules.similarity.metrics.impl.SimilarityMetrics;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;

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
