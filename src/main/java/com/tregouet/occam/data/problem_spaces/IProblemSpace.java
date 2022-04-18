package com.tregouet.occam.data.problem_spaces;

import java.util.NavigableSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.IRepresentation;

public interface IProblemSpace {

	DirectedAcyclicGraph<IProblemState, AProblemStateTransition> asGraph();

	ICompleteRepresentation getCompleteRepresentationWithID(int iD);

	/**
	 *
	 * @return states sorted by decreasing score (and hashCode if score is equal)
	 */
	NavigableSet<IRepresentation> getSortedSetOfStates();

	IProblemState getStateWithID(int iD);

}
