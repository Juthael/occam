package com.tregouet.occam.alg.builders.problem_spaces.transitions;

import com.tregouet.occam.alg.builders.problem_spaces.transitions.impl.UsePartialOrder;

public class ProblemTransitionBuilderFactory {

	public static final ProblemTransitionBuilderFactory INSTANCE = new ProblemTransitionBuilderFactory();

	private ProblemTransitionBuilderFactory() {
	}

	public ProblemTransitionBuilder apply(ProblemTransitionBuilderStrategy strategy) {
		switch (strategy) {
		case USE_PARTIAL_ORDER:
			return UsePartialOrder.INSTANCE;
		default:
			return null;
		}
	}

}
