package com.tregouet.occam.data.problem_spaces.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.data.problem_spaces.ACategorizationTransition;
import com.tregouet.occam.data.problem_spaces.ICategorizationGoalState;
import com.tregouet.occam.data.problem_spaces.ICategorizationProblemSpace;
import com.tregouet.occam.data.problem_spaces.ICategorizationState;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.IPartialRepresentation;

public class CategorizationProblemSpace implements ICategorizationProblemSpace {

	private final DirectedAcyclicGraph<ICategorizationState, ACategorizationTransition> problemGraph;
	private final PartialRepresentationLateSetter partialRepresentationLateSetter;
	
	public CategorizationProblemSpace( 
			DirectedAcyclicGraph<ICategorizationState, ACategorizationTransition> problemGraph,
			PartialRepresentationLateSetter partialRepresentationLateSetter) {
		this.problemGraph = problemGraph;
		this.partialRepresentationLateSetter = partialRepresentationLateSetter;
	}
	
	@Override
	public DirectedAcyclicGraph<ICategorizationState, ACategorizationTransition> asGraph() {
		return problemGraph;
	}

	@Override
	public ICategorizationState getCategorizationStateWithID(int iD) {
		for (ICategorizationState categorizationState : problemGraph.vertexSet()) {
			if (categorizationState.id() == iD) {
				if (!(categorizationState instanceof ICategorizationGoalState))
					partialRepresentationLateSetter.accept((IPartialRepresentation) categorizationState);
				return categorizationState;	
			}
		}
		return null;
	}

	@Override
	public ICompleteRepresentation getCompleteRepresentationWithID(int iD) {
		for (ICategorizationState categorizationState : problemGraph.vertexSet()) {
			if (categorizationState.id() == iD && problemGraph.outDegreeOf(categorizationState) == 0) {
				return (ICompleteRepresentation) categorizationState;
			}
		}
		return null;
	}

	@Override
	public boolean addRepresentation(String representationAsRegEx) {
		// NOT IMPLEMENTED YET
		return false;
	}

}
