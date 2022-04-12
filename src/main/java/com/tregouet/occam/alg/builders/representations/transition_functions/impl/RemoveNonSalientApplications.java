package com.tregouet.occam.alg.builders.representations.transition_functions.impl;

import java.util.Set;
import java.util.stream.Collectors;

import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.Salience;
import com.tregouet.occam.data.representations.transitions.TransitionType;

public class RemoveNonSalientApplications extends AbstractTransFuncBuilder implements RepresentationTransFuncBuilder {

	@Override
	protected Set<IConceptTransition> filter(Set<IConceptTransition> transitions) {
		return transitions
				.stream()
				.filter(t -> !isNonSalientApplication(t))
				.collect(Collectors.toSet());
	}
	
	private boolean isNonSalientApplication(IConceptTransition transition) {
		if (transition.type() == TransitionType.APPLICATION
				&& (transition.getSalience() == Salience.REDUNDANT
					|| transition.getSalience() == Salience.HIDDEN))
			return true;
		return false;
	}

}
