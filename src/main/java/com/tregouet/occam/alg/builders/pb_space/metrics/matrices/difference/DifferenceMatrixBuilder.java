package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.difference;

import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public interface DifferenceMatrixBuilder {
	
	DifferenceMatrixBuilder setUp(Set<Integer> particularIDs, 
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph);
	
	double[][] getDifferenceMatrix();

}
