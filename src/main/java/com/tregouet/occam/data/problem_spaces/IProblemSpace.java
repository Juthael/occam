package com.tregouet.occam.data.problem_spaces;

import java.util.Map;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.logical_structures.orders.total.IScore;
import com.tregouet.occam.data.representations.ICompleteRepresentation;

public interface IProblemSpace {
	
	DirectedAcyclicGraph<IProblemState, AProblemStateTransition> asGraph();
	
	IProblemState getCategorizationStateWithID(int iD);
	
	ICompleteRepresentation getCompleteRepresentationWithID(int iD);
	
	boolean addRepresentation(String representationAsRegEx);	
	
	Map<IProblemState, IScore<?>> mapRepresentationToScore();

}
