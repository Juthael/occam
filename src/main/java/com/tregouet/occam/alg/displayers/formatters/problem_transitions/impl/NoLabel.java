package com.tregouet.occam.alg.displayers.formatters.problem_transitions.impl;

import com.tregouet.occam.alg.displayers.formatters.problem_transitions.ProblemTransitionLabeller;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public class NoLabel implements ProblemTransitionLabeller {
	
	public static final NoLabel INSTANCE = new NoLabel();
	
	private NoLabel() {
	}

	@Override
	public String apply(AProblemStateTransition transitions) {
		return new String();
	}

}
