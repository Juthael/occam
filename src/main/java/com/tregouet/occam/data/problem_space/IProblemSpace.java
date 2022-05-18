package com.tregouet.occam.data.problem_space;

import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public interface IProblemSpace {

	DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> asGraph();

	IRepresentation getRepresentationWithID(int iD);
	
	Set<IRepresentation> sort(int representationID);

}
