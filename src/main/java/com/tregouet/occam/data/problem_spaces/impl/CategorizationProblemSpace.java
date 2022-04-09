package com.tregouet.occam.data.problem_spaces.impl;

import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.partial_representation.PartialRepresentationLateSetter;
import com.tregouet.occam.data.problem_spaces.ACategorizationStateTransition;
import com.tregouet.occam.data.problem_spaces.ICategorizationGoalState;
import com.tregouet.occam.data.problem_spaces.ICategorizationProblemSpace;
import com.tregouet.occam.data.problem_spaces.ICategorizationState;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.IPartialRepresentation;
import com.tregouet.occam.data.representations.concepts.IContextObject;

public class CategorizationProblemSpace implements ICategorizationProblemSpace {

	private final Set<IContextObject> context;
	private final DirectedAcyclicGraph<ICategorizationState, ACategorizationStateTransition> problemGraph;
	private final PartialRepresentationLateSetter partialRepresentationLateSetter;
	
	public CategorizationProblemSpace(Set<IContextObject> context, 
			DirectedAcyclicGraph<ICategorizationState, ACategorizationStateTransition> problemGraph,
			PartialRepresentationLateSetter partialRepresentationLateSetter) {
		this.context = context;
		this.problemGraph = problemGraph;
		this.partialRepresentationLateSetter = partialRepresentationLateSetter;
	}
	
	@Override
	public DirectedAcyclicGraph<ICategorizationState, ACategorizationStateTransition> asGraph() {
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

	public Set<IContextObject> getContext() {
		return context;
	}

}
