package com.tregouet.occam.alg.builders.problem_spaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.next_closure.IClosedSetsFinder;
import com.tregouet.next_closure.impl.NextClosure;
import com.tregouet.occam.alg.builders.problem_spaces.ProblemSpaceBuilder;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.TransitionBuilder;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemSpace;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.ICompleteRepresentations;
import com.tregouet.occam.data.representations.impl.PartialRepresentation;

public class GaloisLatticeOfRepresentations implements ProblemSpaceBuilder {

	public static final ProblemSpaceBuilder INSTANCE = new GaloisLatticeOfRepresentations();

	private static List<IProblemState> buildCategorisationStates(ICompleteRepresentations completeRepresentations,
			LinkedHashMap<Set<Integer>, Set<IPartition>> closedSets) {
		List<IProblemState> states = new ArrayList<>();
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

	private static Map<Integer, Set<IPartition>> setCompleteRepIdx2PartitionMap(
			ICompleteRepresentations completeRepresentations) {
		Map<Integer, Set<IPartition>> mapping = new HashMap<>();
		for (ICompleteRepresentation rep : completeRepresentations.getSortedRepresentations())
			mapping.put(rep.id(), rep.getPartitions());
		return mapping;
	}

	private GaloisLatticeOfRepresentations() {
	}

	@Override
	public IProblemSpace apply(ICompleteRepresentations completeRepresentations) {
		Map<Integer, Set<IPartition>> completeRepIdx2Partitions = setCompleteRepIdx2PartitionMap(
				completeRepresentations);
		IClosedSetsFinder<Integer, IPartition> closedSetsFinder = new NextClosure<>();
		LinkedHashMap<Set<Integer>, Set<IPartition>> lecticallyOrderedClosedSets = closedSetsFinder
				.apply(completeRepIdx2Partitions);
		List<IProblemState> topoOrderedStates = buildCategorisationStates(completeRepresentations,
				lecticallyOrderedClosedSets);
		TransitionBuilder transBldr = ProblemSpaceBuilder.getCategorizationTransitionBuilder();
		Set<AProblemStateTransition> transitions = transBldr.apply(topoOrderedStates);
		return ProblemSpaceBuilder.build(topoOrderedStates, transitions);
	}

}
