package com.tregouet.occam.alg.displayers.graph_labellers.problem_transitions.impl;

import com.tregouet.occam.alg.displayers.graph_labellers.problem_transitions.ProblemTransitionLabeller;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;

public class WeightOnly implements ProblemTransitionLabeller {
	
	public static final WeightOnly INSTANCE = new WeightOnly();
	
	private WeightOnly() {		
	}

	@Override
	public String apply(AProblemStateTransition transition) {
		return transition.weight().toString();
	}

}
