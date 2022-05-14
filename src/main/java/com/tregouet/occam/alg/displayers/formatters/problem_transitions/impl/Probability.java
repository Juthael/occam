package com.tregouet.occam.alg.displayers.formatters.problem_transitions.impl;

import java.text.DecimalFormat;

import com.tregouet.occam.alg.displayers.formatters.problem_transitions.ProblemTransitionLabeller;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;

public class Probability implements ProblemTransitionLabeller {
	
	public static final Probability INSTANCE = new Probability();
	
	private Probability() {
	}
	
	private static final DecimalFormat df = new DecimalFormat("#.###");

	private static String round(double nb) {
		return df.format(nb).toString();
	}

	@Override
	public String apply(AProblemStateTransition transition) {
		double sourceStateScore = transition.getSource().score().value();
		double targetStateScore = transition.getTarget().score().value();
		if (sourceStateScore == 0.0 || targetStateScore == 0.0)
			return Double.toString(0.0);
		return round(targetStateScore / sourceStateScore);
	}

}
