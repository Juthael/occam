package com.tregouet.occam.alg.displayers.formatters.transition_functions.impl;

import java.util.Set;

import com.tregouet.occam.alg.displayers.formatters.transition_functions.TransitionFunctionLabeller;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;

public class DisplayAllTransitions extends AbstractTFLabeller implements TransitionFunctionLabeller {

	@Override
	protected Set<IConceptTransition> complyToOptionalConstraint(Set<IConceptTransition> transitions) {
		return transitions;
	}

}
