package com.tregouet.occam.data.problem_spaces;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.representations.ICompleteRepresentation;

public interface ICategorizationProblemSpace {
	
	DirectedAcyclicGraph<ICategorizationState, ACategorizationStateTransition> asGraph();
	
	ICategorizationState getCategorizationStateWithID(int iD);
	
	ICompleteRepresentation getCompleteRepresentationWithID(int iD);
	
	boolean addRepresentation(String representationAsRegEx);	

}
