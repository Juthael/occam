package com.tregouet.occam.alg.builders.metricsDEP.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.metricsDEP.SimilarityMetricsBuilderDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.symmetrical_sim.SimilarityMatrixBuilderDEP;
import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.modules.similarity.metrics.impl.SimilarityMetrics;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;

public class SimThenDiffThenTypicalityDEP implements SimilarityMetricsBuilderDEP {

	public static final SimThenDiffThenTypicalityDEP INSTANCE = new SimThenDiffThenTypicalityDEP();

	private SimThenDiffThenTypicalityDEP() {
	}

	@Override
	public ISimilarityMetrics apply(IConceptLattice lattice, DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> pbGraph,
			double[][] differenceMatrixParam) {
		double[][] similarityMatrix;
		String[][] referenceMatrix;
		double[][] asymmetricalSimilarityMatrix;
		double[][] differenceMatrix;
		double[] typicalityVector;
		SimilarityMatrixBuilderDEP simMatrixBuilder =
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
		return null;
		// return new SimilarityMetrics(similarityMatrix, referenceMatrix, asymmetricalSimilarityMatrix, differenceMatrix, typicalityVector);
	}

}
