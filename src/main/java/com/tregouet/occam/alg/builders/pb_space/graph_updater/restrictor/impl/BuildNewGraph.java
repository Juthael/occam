package com.tregouet.occam.alg.builders.pb_space.graph_updater.restrictor.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.pb_space.graph_updater.restrictor.ProblemSpaceGraphRestrictor;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;
import com.tregouet.occam.data.problem_space.transitions.impl.ProblemStateTransition;

public class BuildNewGraph implements ProblemSpaceGraphRestrictor {
	
	public static final BuildNewGraph INSTANCE = new BuildNewGraph();
	
	private BuildNewGraph() {
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> apply(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> graph, Set<IRepresentation> restriction) {
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

}
