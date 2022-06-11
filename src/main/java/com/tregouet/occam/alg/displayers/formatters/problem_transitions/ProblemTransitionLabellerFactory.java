package com.tregouet.occam.alg.displayers.formatters.problem_transitions;

import com.tregouet.occam.alg.displayers.formatters.problem_transitions.impl.NoLabel;
import com.tregouet.occam.alg.displayers.formatters.problem_transitions.impl.Probability;
import com.tregouet.occam.alg.displayers.formatters.problem_transitions.impl.Weight;

public class ProblemTransitionLabellerFactory {

	public static final ProblemTransitionLabellerFactory INSTANCE = new ProblemTransitionLabellerFactory();

	private ProblemTransitionLabellerFactory() {
	}

	public ProblemTransitionLabeller apply(ProblemTransitionLabellerStrategy strategy) {
		switch (strategy) {
		case NO_LABEL :
			return NoLabel.INSTANCE;
		case WEIGHT:
			return Weight.INSTANCE;
		case PROBABILITY :
			return Probability.INSTANCE;
		default:
			return null;
		}
	}

}
