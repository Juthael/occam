package com.tregouet.occam.alg.builders.problem_spaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.next_closure.IClosedSetsFinder;
import com.tregouet.next_closure.impl.NextClosure;
import com.tregouet.occam.alg.builders.problem_spaces.CategorizationProblemSpaceBuilder;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.CategorizationTransitionBuilder;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.CategorizationTransitionWeigher;
import com.tregouet.occam.data.problem_spaces.ACategorizationTransition;
import com.tregouet.occam.data.problem_spaces.ICategorizationProblemSpace;
import com.tregouet.occam.data.problem_spaces.ICategorizationState;
import com.tregouet.occam.data.problem_spaces.impl.CategorizationProblemSpace;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.ICompleteRepresentations;
import com.tregouet.occam.data.representations.impl.PartialRepresentation;

public class GaloisLatticeOfRepresentations implements CategorizationProblemSpaceBuilder {
	
	public static final CategorizationProblemSpaceBuilder INSTANCE = new GaloisLatticeOfRepresentations();
	
	private GaloisLatticeOfRepresentations() {
	}

	@Override
	public ICategorizationProblemSpace apply(ICompleteRepresentations completeRepresentations) {
		Map<Integer, Set<IPartition>> completeRepIdx2Partitions = 
				setCompleteRepIdx2PartitionMap(completeRepresentations);
		IClosedSetsFinder<Integer, IPartition> closedSetsFinder = new NextClosure<>(); 
		LinkedHashMap<Set<Integer>, Set<IPartition>> lecticallyOrderedClosedSets = closedSetsFinder.apply(completeRepIdx2Partitions);
		List<ICategorizationState> topoOrderedStates = 
				buildCategorisationStates(completeRepresentations, lecticallyOrderedClosedSets);
		CategorizationTransitionBuilder transBldr = CategorizationProblemSpaceBuilder.getCategorizationTransitionBuilder();
		Set<ACategorizationTransition> transitions = transBldr.apply(topoOrderedStates);
		DirectedAcyclicGraph<ICategorizationState, ACategorizationTransition> problemGraph = new DirectedAcyclicGraph<>(null, null, true);
		Graphs.addAllVertices(problemGraph, topoOrderedStates);
		for (ACategorizationTransition transition : transitions)
			problemGraph.addEdge(transition.getSource(), transition.getTarget(), transition);
		TransitiveReduction.INSTANCE.reduce(problemGraph);
		CategorizationTransitionWeigher weigher = CategorizationProblemSpaceBuilder.getCategorizationTransitionWeigher().setContext(problemGraph);
		for (ACategorizationTransition transition : problemGraph.edgeSet())
			weigher.accept(transition);
		PartialRepresentationLateSetter partialRepLateSetter = CategorizationProblemSpaceBuilder.getPartialRepresentationLateSetter();
		return new CategorizationProblemSpace(problemGraph, partialRepLateSetter);
	}
	
	private static Map<Integer, Set<IPartition>> setCompleteRepIdx2PartitionMap(
			ICompleteRepresentations completeRepresentations) {
		Map<Integer, Set<IPartition>> mapping = new HashMap<>();
		for (ICompleteRepresentation rep : completeRepresentations.getSortedRepresentations())
			mapping.put(rep.id(), rep.getPartitions());
		return mapping;
	}
	
	private static List<ICategorizationState> buildCategorisationStates(
			ICompleteRepresentations completeRepresentations,
			LinkedHashMap<Set<Integer>, Set<IPartition>> closedSets) {
		List<ICategorizationState> states = new ArrayList<>();
		for (Set<Integer> completeRepIdxes : closedSets.keySet()) {
			if (completeRepIdxes.size() == 1)
				states.add(completeRepresentations.get(completeRepIdxes.iterator().next()));
			else {
				Set<ICompleteRepresentation> extent = completeRepresentations.get(completeRepIdxes);
				Set<IPartition> intent = intent(extent);
				states.add(new PartialRepresentation(intent, extent));
			}
		}
		return states;
	}
	
	private static Set<IPartition> intent(Set<ICompleteRepresentation> extent) {
		Set<IPartition> intent = new HashSet<>();
		Iterator<ICompleteRepresentation> extentIte = extent.iterator();
		if (extentIte.hasNext())
			intent.addAll(extentIte.next().getPartitions());
		while (extentIte.hasNext())
			intent.retainAll(extentIte.next().getPartitions());
		return intent;
	}

}
