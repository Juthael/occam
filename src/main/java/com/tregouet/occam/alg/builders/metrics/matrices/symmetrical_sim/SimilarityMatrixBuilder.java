package com.tregouet.occam.alg.builders.metrics.matrices.symmetrical_sim;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;

public interface SimilarityMatrixBuilder {

	String[][] getReferenceMatrix();

	double[][] getSimilarityMatrix();

	SimilarityMatrixBuilder setUp(int setCardinal,
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph);

}
