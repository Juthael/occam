package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences.TransitionSalienceSetter;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.Salience;

public class EveryTransitionIsRule implements TransitionSalienceSetter {
	
	public static final EveryTransitionIsRule INSTANCE = new EveryTransitionIsRule();
	
	private EveryTransitionIsRule() {
	}

	@Override
	public void setSaliencesOf(Set<IConceptTransition> transitions, Set<Integer> particularIDs) {
		for (IConceptTransition transition : transitions)
			transition.setSalience(Salience.TRANSITION_RULE);
	}

}
