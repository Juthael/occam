package com.tregouet.occam.alg.builders.problem_spaces.transitions.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.CategorizationTransitionBuilder;
import com.tregouet.occam.data.problem_spaces.ACategorizationStateTransition;
import com.tregouet.occam.data.problem_spaces.ICategorizationState;
import com.tregouet.occam.data.problem_spaces.impl.CategorizationTransition;
import com.tregouet.occam.data.representations.partitions.IPartition;

public class UsePartialOrder implements CategorizationTransitionBuilder {

	public static final UsePartialOrder INSTANCE = new UsePartialOrder();
	
	private UsePartialOrder() {
	}
	
	@Override
	public Set<ACategorizationStateTransition> apply(Set<ICategorizationState> pbStates) {
		Set<ACategorizationStateTransition> stateTransitions = new HashSet<>();
		List<ICategorizationState> pbStateList = new ArrayList<>(pbStates);
		int nbOfStates = pbStateList.size();
		for (int i = 0 ; i < nbOfStates - 1 ; i++) {
			ICategorizationState iState = pbStateList.get(i);
			for (int j = i + 1 ; j < nbOfStates ; j++) {
				ICategorizationState jState = pbStateList.get(j);
				Integer comparison = iState.compareTo(jState);
				if (comparison != null) {
					if (comparison > 0)
						stateTransitions.add(buildTransition(jState, iState));
					else if (comparison < 0) {
						stateTransitions.add(buildTransition(iState, jState));
					}
				}
			}
		}
		return stateTransitions;
	}
	
	private ACategorizationStateTransition buildTransition(ICategorizationState lowerBound, ICategorizationState upperBound) {
		Integer sourceID = lowerBound.id();
		Integer targetID = upperBound.id();
		Set<IPartition> partitions = new HashSet<>(
				Sets.difference(upperBound.getPartitions(), lowerBound.getPartitions()));
		return new CategorizationTransition(sourceID, targetID, partitions);
	}

}
