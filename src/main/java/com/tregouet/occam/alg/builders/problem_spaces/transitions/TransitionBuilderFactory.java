package com.tregouet.occam.alg.builders.problem_spaces.transitions;

import com.tregouet.occam.alg.builders.problem_spaces.transitions.impl.UsePartialOrder;

public class TransitionBuilderFactory {

	public static final TransitionBuilderFactory INSTANCE = new TransitionBuilderFactory();

	private TransitionBuilderFactory() {
	}

	public TransitionBuilder apply(TransitionBuilderStrategy strategy) {
		switch (strategy) {
		case USE_PARTIAL_ORDER:
			return UsePartialOrder.INSTANCE;
		default:
			return null;
		}
	}

}
