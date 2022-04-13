package com.tregouet.occam.alg.displayers.transition_function.impl;

import java.util.Set;

import com.tregouet.occam.alg.displayers.transition_function.TransitionFunctionDisplayer;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;

public class DisplayAllTransitions extends AbstractTFDisplayer implements TransitionFunctionDisplayer {

	@Override
	protected Set<IConceptTransition> filter(Set<IConceptTransition> transitions) {
		return transitions;
	}

}
