package com.tregouet.occam.data.problem_spaces.impl;

import java.util.NavigableSet;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IGoalState;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.IPartialRepresentation;
import com.tregouet.occam.data.representations.IRepresentation;

public class ProblemSpace implements IProblemSpace {

	private final DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemGraph;
	private final PartialRepresentationLateSetter partialRepresentationLateSetter;
	private final NavigableSet<IRepresentation> representations = new TreeSet<>();

	public ProblemSpace(DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemGraph,
			PartialRepresentationLateSetter partialRepresentationLateSetter) {
		this.problemGraph = problemGraph;
		this.partialRepresentationLateSetter = partialRepresentationLateSetter;
		for (IProblemState state : problemGraph)
			representations.add((IRepresentation) state);
	}

	@Override
	public DirectedAcyclicGraph<IProblemState, AProblemStateTransition> asGraph() {
		return problemGraph;
	}

	@Override
	public ICompleteRepresentation getCompleteRepresentationWithID(int iD) {
		for (IProblemState problemState : problemGraph.vertexSet()) {
			if (problemState.id() == iD && problemGraph.outDegreeOf(problemState) == 0) {
				return (ICompleteRepresentation) problemState;
			}
		}
		return null;
	}

	@Override
	public NavigableSet<IRepresentation> getSortedSetOfStates() {
		return representations;
	}

	@Override
	public IProblemState getStateWithID(int iD) {
		for (IProblemState problemState : problemGraph.vertexSet()) {
			if (problemState.id() == iD) {
				if (!(problemState instanceof IGoalState))
					partialRepresentationLateSetter.accept((IPartialRepresentation) problemState);
				return problemState;
			}
		}
		return null;
	}

}
