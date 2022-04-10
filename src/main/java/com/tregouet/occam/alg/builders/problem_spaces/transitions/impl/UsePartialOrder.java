package com.tregouet.occam.alg.builders.problem_spaces.transitions.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.CategorizationTransitionBuilder;
import com.tregouet.occam.data.problem_spaces.ACategorizationTransition;
import com.tregouet.occam.data.problem_spaces.ICategorizationState;
import com.tregouet.occam.data.problem_spaces.impl.CategorizationTransition;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;

public class UsePartialOrder implements CategorizationTransitionBuilder {

	public static final UsePartialOrder INSTANCE = new UsePartialOrder();
	
	private UsePartialOrder() {
	}
	
	@Override
	public Set<ACategorizationTransition> apply(List<ICategorizationState> topoOrderedStates) {
		Set<ACategorizationTransition> transitions = new HashSet<>();
		int nbOfStates = topoOrderedStates.size();
		for (int i = 0 ; i < nbOfStates - 1 ; i++) {
			ICategorizationState iState = topoOrderedStates.get(i);
			Set<IPartition> iStatePartitions = iState.getPartitions();
			for (int j = i + 1 ; j < nbOfStates ; j++) {
				ICategorizationState jState = topoOrderedStates.get(j);
				Set<IPartition> jStatePartitions = jState.getPartitions();
				if (jStatePartitions.containsAll(iStatePartitions)) {
					transitions.add(
							new CategorizationTransition(
									iState, 
									jState, 
									new HashSet<>(Sets.difference(jStatePartitions, iStatePartitions))));
				}
			}
		}
		return transitions;
	}

}
