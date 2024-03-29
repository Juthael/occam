package com.tregouet.occam.alg.builders.categorizer.graph_updater.expander.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.categorizer.graph_updater.expander.ProblemSpaceGraphExpander;
import com.tregouet.occam.data.modules.sorting.transitions.AProblemStateTransition;
import com.tregouet.occam.data.modules.sorting.transitions.impl.ProblemStateTransition;
import com.tregouet.occam.data.modules.sorting.transitions.partitions.IPartition;
import com.tregouet.occam.data.structures.representations.IRepresentation;

public class AddNewStatesThenBuildTransitions implements ProblemSpaceGraphExpander {

	public static final AddNewStatesThenBuildTransitions INSTANCE = new AddNewStatesThenBuildTransitions();

	private AddNewStatesThenBuildTransitions() {
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> apply(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> pbGraph, Set<IRepresentation> newRepresentations) {
		List<IRepresentation> previousRepList = new ArrayList<>(pbGraph.vertexSet());
		List<IRepresentation> newRepList = new ArrayList<>(newRepresentations);
		Graphs.addAllVertices(pbGraph, newRepresentations);
		//build transitions between new reps
		for (int i = 0 ; i < newRepList.size() - 1 ; i++) {
			IRepresentation iNewRep = newRepList.get(i);
			Set<IPartition> iNewRepPartitions = iNewRep.getPartitions();
			for (int j = i + 1 ; j < newRepList.size() ; j++) {
				IRepresentation jNewRep = newRepList.get(j);
				Set<IPartition> jNewRepPartitions = jNewRep.getPartitions();
				if (!iNewRep.isFullyDeveloped() && jNewRepPartitions.containsAll(iNewRepPartitions)) {
					pbGraph.addEdge(iNewRep, jNewRep,
							new ProblemStateTransition(iNewRep, jNewRep,
									new HashSet<>(Sets.difference(jNewRepPartitions, iNewRepPartitions))));
				}
				else if (!jNewRep.isFullyDeveloped() && iNewRepPartitions.containsAll(jNewRepPartitions)) {
					pbGraph.addEdge(jNewRep, iNewRep,
							new ProblemStateTransition(jNewRep, iNewRep,
									new HashSet<>(Sets.difference(iNewRepPartitions, jNewRepPartitions))));
				}
			}
		}
		//build transitions between previous reps and new reps
		Iterator<IRepresentation> previousRepIte = previousRepList.iterator();
		while (previousRepIte.hasNext()) {
			IRepresentation previousRep = previousRepIte.next();
			if (!previousRep.isFullyDeveloped()) {
				Set<IPartition> previousRepPartitions = previousRep.getPartitions();
				for (IRepresentation newRep : newRepresentations) {
					Set<IPartition> newRepPartitions = newRep.getPartitions();
					if (!newRep.equals(previousRep) && newRepPartitions.containsAll(previousRepPartitions)) {
						pbGraph.addEdge(previousRep, newRep,
								new ProblemStateTransition(previousRep, newRep,
										new HashSet<>(Sets.difference(newRepPartitions, previousRepPartitions))));
					}
				}
			}
		}
		TransitiveReduction.INSTANCE.reduce(pbGraph);
		return pbGraph;
	}

}
