package com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.symmetrical_sim;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;

public interface SimilarityMatrixBuilderDEP {

	String[][] getReferenceMatrix();

	double[][] getSimilarityMatrix();

	SimilarityMatrixBuilderDEP setUp(int setCardinal,
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph);

}
