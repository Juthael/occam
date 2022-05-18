package com.tregouet.occam.data.problem_space.impl;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public class ProblemSpace implements IProblemSpace {

	private final ProblemSpaceExplorer problemSpaceExplorer;
	

	public ProblemSpace(ProblemSpaceExplorer problemSpaceExplorer) {
		this.problemSpaceExplorer = problemSpaceExplorer;
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> asGraph() {
		return problemSpaceExplorer.getProblemSpaceGraph();
	}

	@Override
	public IRepresentation getRepresentationWithID(int iD) {
		for (IRepresentation problemState : problemSpaceExplorer.getProblemSpaceGraph().vertexSet()) {
			if (problemState.iD() == iD) {
				return (IRepresentation) problemState;
			}
		}
		return null;
	}

	@Override
	public Set<IRepresentation> sort(int representationID) {
		Set<IRepresentation> beforeSorting = new HashSet<>(problemSpaceExplorer.getProblemSpaceGraph().vertexSet());
		Boolean result = problemSpaceExplorer.apply(representationID);
		if (result == null)
			return null;
		if (result == false)
			return new HashSet<>();
		else {
			Set<IRepresentation> afterSorting = new HashSet<>(problemSpaceExplorer.getProblemSpaceGraph().vertexSet());
			return new HashSet<IRepresentation>(Sets.difference(afterSorting, beforeSorting));	
		}
	}

}
