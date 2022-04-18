package com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.impl;

import java.util.Set;
import java.util.stream.Collectors;

import com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.TransitionFunctionLabeller;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.Salience;
import com.tregouet.occam.data.representations.transitions.TransitionType;

public class RetainSalientApp extends AbstractTFLabeller implements TransitionFunctionLabeller {

	@Override
	protected Set<IConceptTransition> filter(Set<IConceptTransition> transitions) {
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
