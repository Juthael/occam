package com.tregouet.occam.alg.builders.problem_spaces.transitions.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.TransitionBuilder;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.problem_spaces.impl.ProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;

public class UsePartialOrder implements TransitionBuilder {

	public static final UsePartialOrder INSTANCE = new UsePartialOrder();

	private UsePartialOrder() {
	}

	@Override
	public Set<AProblemStateTransition> apply(List<IProblemState> topoOrderedStates) {
		Set<AProblemStateTransition> transitions = new HashSet<>();
		int nbOfStates = topoOrderedStates.size();
		for (int i = 0 ; i < nbOfStates - 1 ; i++) {
			IProblemState iState = topoOrderedStates.get(i);
			Set<IPartition> iStatePartitions = iState.getPartitions();
			for (int j = i + 1 ; j < nbOfStates ; j++) {
				IProblemState jState = topoOrderedStates.get(j);
				Set<IPartition> jStatePartitions = jState.getPartitions();
				if (jStatePartitions.containsAll(iStatePartitions)) {
					transitions.add(
							new ProblemStateTransition(
									iState,
									jState,
									new HashSet<>(Sets.difference(jStatePartitions, iStatePartitions))));
				}
			}
		}
		return transitions;
	}

}
