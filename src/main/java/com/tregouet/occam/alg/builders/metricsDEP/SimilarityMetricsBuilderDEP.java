package com.tregouet.occam.alg.builders.metricsDEP;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.asymetrical_sim.AsymmetricalSimilarityMatrixBuilderDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.difference.DifferenceMatrixBuilderDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.symmetrical_sim.SimilarityMatrixBuilderDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.typicality.TypicalityVectorBuilderDEP;
import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;

public interface SimilarityMetricsBuilderDEP {

	ISimilarityMetrics apply(IConceptLattice lattice, DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> pbGraph,
			double[][] differenceMatrixParam);

	public static AsymmetricalSimilarityMatrixBuilderDEP asymmetricalSimilarityBuilder() {
		return BuildersAbstractFactory.INSTANCE.getAsymmetricalSimilarityMatrixBuilder();
	}

	public static DifferenceMatrixBuilderDEP differenceMatrixBuilderDEP() {
		return BuildersAbstractFactory.INSTANCE.getDifferenceMatrixBuilder();
	}

	public static SimilarityMatrixBuilderDEP similarityMatrixBuilderDEP() {
		return BuildersAbstractFactory.INSTANCE.getSimilarityMatrixBuilder();
	}

	public static TypicalityVectorBuilderDEP typicalityVectorBuilderDEP() {
		return BuildersAbstractFactory.INSTANCE.getTypicalityVectorBuilder();
	}

}
