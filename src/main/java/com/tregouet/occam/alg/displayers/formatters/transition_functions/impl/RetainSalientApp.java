package com.tregouet.occam.alg.displayers.formatters.transition_functions.impl;

import java.util.Set;
import java.util.stream.Collectors;

import com.tregouet.occam.alg.displayers.formatters.transition_functions.TransitionFunctionLabeller;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.Salience;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;

public class RetainSalientApp extends AbstractTFLabeller implements TransitionFunctionLabeller {

	@Override
	protected Set<IConceptTransition> complyToOptionalConstraint(Set<IConceptTransition> transitions) {
		return transitions.stream().filter(t -> isASalientApplication(t)).collect(Collectors.toSet());
	}

	private boolean isASalientApplication(IConceptTransition transition) {
		if ((transition.type() == TransitionType.APPLICATION && (transition.getSalience() == Salience.COMMON_FEATURE
				|| transition.getSalience() == Salience.TRANSITION_RULE))
				|| (transition.type() == TransitionType.INITIAL))
			return true;
		return false;
	}

}
