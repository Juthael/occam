package com.tregouet.occam.data.problem_spaces.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IContextObject;

public class ProblemSpace implements IProblemSpace {

	private final ProblemSpaceExplorer problemSpaceExplorer;
	private DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph;
	

	public ProblemSpace(Collection<IContextObject> context, ProblemSpaceExplorer problemSpaceExplorer) {
		this.problemSpaceExplorer = problemSpaceExplorer;
		this.problemGraph = problemSpaceExplorer.initialize(context);
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
	public Set<IRepresentation> sort(int representationID) {
		Set<IRepresentation> beforeSorting = new HashSet<>(problemGraph.vertexSet());
		problemGraph = problemSpaceExplorer.apply(representationID);
		Set<IRepresentation> afterSorting = new HashSet<>(problemGraph.vertexSet());
		return new HashSet<IRepresentation>(Sets.difference(afterSorting, beforeSorting));
	}

}
