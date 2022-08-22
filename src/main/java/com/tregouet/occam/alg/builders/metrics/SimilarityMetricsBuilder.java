package com.tregouet.occam.alg.builders.metrics;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.metrics.matrices.asymetrical_sim.AsymmetricalSimilarityMatrixBuilder;
import com.tregouet.occam.alg.builders.metrics.matrices.difference.DifferenceMatrixBuilder;
import com.tregouet.occam.alg.builders.metrics.matrices.symmetrical_sim.SimilarityMatrixBuilder;
import com.tregouet.occam.alg.builders.metrics.matrices.typicality.TypicalityVectorBuilder;
import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;

public interface SimilarityMetricsBuilder {

	ISimilarityMetrics apply(IConceptLattice lattice, DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> pbGraph,
			double[][] differenceMatrix);

	public static AsymmetricalSimilarityMatrixBuilder asymmetricalSimilarityBuilder() {
		return BuildersAbstractFactory.INSTANCE.getAsymmetricalSimilarityMatrixBuilder();
	}

	public static DifferenceMatrixBuilder differenceMatrixBuilder() {
		return BuildersAbstractFactory.INSTANCE.getDifferenceMatrixBuilder();
	}

	public static SimilarityMatrixBuilder similarityMatrixBuilder() {
		return BuildersAbstractFactory.INSTANCE.getSimilarityMatrixBuilder();
	}

	public static TypicalityVectorBuilder typicalityVectorBuilder() {
		return BuildersAbstractFactory.INSTANCE.getTypicalityVectorBuilder();
	}

}
