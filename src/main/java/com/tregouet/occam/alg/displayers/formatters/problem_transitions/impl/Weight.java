package com.tregouet.occam.alg.displayers.formatters.problem_transitions.impl;

import com.tregouet.occam.alg.displayers.formatters.problem_transitions.ProblemTransitionLabeller;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;

public class Weight implements ProblemTransitionLabeller {

	public static final Weight INSTANCE = new Weight();

	private Weight() {
	}

	@Override
	public String apply(AProblemStateTransition transition) {
		return transition.weight().toString();
	}

}
