package com.tregouet.occam.data.problem_spaces;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.representations.ICompleteRepresentation;

public interface IProblemSpace {
	
	DirectedAcyclicGraph<IProblemState, AProblemStateTransition> asGraph();
	
	IProblemState getCategorizationStateWithID(int iD);
	
	ICompleteRepresentation getCompleteRepresentationWithID(int iD);

}
