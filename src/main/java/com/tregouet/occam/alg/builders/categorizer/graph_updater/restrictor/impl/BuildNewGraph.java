package com.tregouet.occam.alg.builders.categorizer.graph_updater.restrictor.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.categorizer.graph_updater.restrictor.ProblemSpaceGraphRestrictor;
import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.modules.categorization.transitions.impl.ProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;

public class BuildNewGraph implements ProblemSpaceGraphRestrictor {

	public static final BuildNewGraph INSTANCE = new BuildNewGraph();

	private BuildNewGraph() {
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> apply(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> graph, Set<IRepresentation> restriction) {
		restriction.add(getRootOf(graph)); //if no root, meaningless
		DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> newGraph =
				new DirectedAcyclicGraph<>(null, null, false);
		Graphs.addAllVertices(newGraph, restriction);
		List<IRepresentation> restrictionList = new ArrayList<>(restriction);
		for (int i = 0 ; i < restriction.size() - 1 ; i++) {
			IRepresentation iRepresentation = restrictionList.get(i);
			for (int j = i + 1 ; j < restriction.size() ; j++) {
				IRepresentation jRepresentation = restrictionList.get(j);
				Integer ijComparison = iRepresentation.compareTo(jRepresentation);
				if (ijComparison != null) {
					if (ijComparison < 0) {
						newGraph.addEdge(iRepresentation, jRepresentation,
								new ProblemStateTransition(iRepresentation, jRepresentation,
										new HashSet<>(Sets.difference(
												jRepresentation.getPartitions(),
												iRepresentation.getPartitions()))));
					}
					else {
						//ijComparison > 0
						newGraph.addEdge(jRepresentation, iRepresentation,
								new ProblemStateTransition(jRepresentation, iRepresentation,
										new HashSet<>(Sets.difference(
												iRepresentation.getPartitions(),
												jRepresentation.getPartitions()))));
					}
				}
			}
		}
		TransitiveReduction.INSTANCE.reduce(newGraph);
		return newGraph;
	}

	private static IRepresentation getRootOf(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> graph) {
		for (IRepresentation rep : graph.vertexSet()) {
			if (graph.inDegreeOf(rep) == 0)
				return rep;
		}
		return null; //can never happen
	}

}
