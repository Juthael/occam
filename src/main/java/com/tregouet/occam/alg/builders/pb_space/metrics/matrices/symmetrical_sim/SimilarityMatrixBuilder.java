package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.symmetrical_sim;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public interface SimilarityMatrixBuilder {
	
	SimilarityMatrixBuilder setUp(int setCardinal, 
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph);
	
	double[][] getSimilarityMatrix();
	
	String[][] getReferenceMatrix();

}
