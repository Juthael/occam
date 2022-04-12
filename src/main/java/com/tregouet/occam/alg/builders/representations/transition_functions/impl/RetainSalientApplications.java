package com.tregouet.occam.alg.builders.representations.transition_functions.impl;

import java.util.Set;
import java.util.stream.Collectors;

import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.Salience;
import com.tregouet.occam.data.representations.transitions.TransitionType;

public class RetainSalientApplications extends AbstractTransFuncBuilder implements RepresentationTransFuncBuilder {

	@Override
	protected Set<IConceptTransition> filter(Set<IConceptTransition> transitions) {
		return transitions.stream()
				.filter(t -> isASalientApplication(t))
				.collect(Collectors.toSet());
	}
	
	private boolean isASalientApplication(IConceptTransition transition) {
		if (transition.type() == TransitionType.APPLICATION
				&& (transition.getSalience() == Salience.COMMON_FEATURE 
					|| transition.getSalience() == Salience.TRANSITION_RULE))
			return true;
		if (transition.type() == TransitionType.INITIAL)
			return true;
		return false;
	}

}
