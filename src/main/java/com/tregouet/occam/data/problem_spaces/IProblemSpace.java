package com.tregouet.occam.data.problem_spaces;

import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.representations.IRepresentation;

public interface IProblemSpace {

	DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> asGraph();

	IRepresentation getRepresentationWithID(int iD);
	
	Set<IRepresentation> sort(int representationID);

}
