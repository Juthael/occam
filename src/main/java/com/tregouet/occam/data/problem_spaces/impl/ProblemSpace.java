package com.tregouet.occam.data.problem_spaces.impl;

import java.util.NavigableSet;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.representations.IRepresentation;

public class ProblemSpace implements IProblemSpace {

	private final DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph;
	private final NavigableSet<IRepresentation> representations = new TreeSet<>();

	public ProblemSpace(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		this.problemGraph = problemGraph;
		for (IRepresentation state : problemGraph)
			representations.add((IRepresentation) state);
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> asGraph() {
		return problemGraph;
	}

	@Override
	public IRepresentation getRepresentationWithID(int iD) {
		for (IRepresentation problemState : problemGraph.vertexSet()) {
			if (problemState.id() == iD) {
				return (IRepresentation) problemState;
			}
		}
		return null;
	}

	@Override
	public NavigableSet<IRepresentation> getSortedSetOfStates() {
		return representations;
	}

}
