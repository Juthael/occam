package com.tregouet.occam.alg.displayers.problem_transitions.impl;

import com.tregouet.occam.alg.displayers.problem_transitions.ProblemTransitionDisplayer;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;

public class WeightOnly implements ProblemTransitionDisplayer {
	
	public static final WeightOnly INSTANCE = new WeightOnly();
	
	private WeightOnly() {		
	}

	@Override
	public String apply(AProblemStateTransition transition) {
		return transition.weight().toString();
	}

}
