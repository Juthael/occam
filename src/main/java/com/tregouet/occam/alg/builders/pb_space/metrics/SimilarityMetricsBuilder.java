package com.tregouet.occam.alg.builders.pb_space.metrics;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.asymetrical_sim.AsymmetricalSimilarityMatrixBuilder;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.difference.DifferenceMatrixBuilder;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.symmetrical_sim.SimilarityMatrixBuilder;
import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.typicality.TypicalityVectorBuilder;
import com.tregouet.occam.data.problem_space.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

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
