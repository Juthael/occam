package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences;

import java.util.Set;

import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;

@FunctionalInterface
public interface TransitionSalienceSetter {
	
	public void setSaliencesOf(Set<IConceptTransition> transitions, Set<Integer> particularIDs);

}
