package com.tregouet.occam.alg.builders.pb_space.graph_updater.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.pb_space.graph_updater.ProblemSpaceGraphUpdater;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;
import com.tregouet.occam.data.problem_space.transitions.impl.ProblemStateTransition;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;

public class BuildNewTransitions implements ProblemSpaceGraphUpdater {
	
	public static final BuildNewTransitions INSTANCE = new BuildNewTransitions();
	
	private BuildNewTransitions() {
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
