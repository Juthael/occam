package com.tregouet.occam.alg.builders.pb_space.impl;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;

public class RemoveMeaningless extends RebuildFromScratch implements ProblemSpaceExplorer {

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
