package com.tregouet.occam.alg.displayers.formatters.problem_transitions.impl;

import java.text.DecimalFormat;

import com.tregouet.occam.alg.displayers.formatters.problem_transitions.ProblemTransitionLabeller;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public class Weight implements ProblemTransitionLabeller {

	public static final Weight INSTANCE = new Weight();

	public static final DecimalFormat df = new DecimalFormat("#.####");

	private Weight() {
	}

	@Override
	public String apply(AProblemStateTransition transition) {
		return round(transition.weight());
	}

	public static String round(double nb) {
		return df.format(nb).toString();
	}

}
