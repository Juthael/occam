package com.tregouet.occam.alg.builders.pb_space.impl;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public class RemoveUninformative extends RemoveMeaningless implements ProblemSpaceExplorer {
	
	public RemoveUninformative() {
	}

	@Override
	protected void filter(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		Set<IRepresentation> representations = new HashSet<>(problemGraph.vertexSet());
		for (IRepresentation representation : representations) {
			if (representation.score().value() == 0.0)
				problemGraph.removeVertex(representation);
		}
	}

}
