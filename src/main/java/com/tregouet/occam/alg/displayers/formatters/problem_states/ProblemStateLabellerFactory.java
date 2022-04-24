package com.tregouet.occam.alg.displayers.formatters.problem_states;

import com.tregouet.occam.alg.displayers.formatters.problem_states.impl.AsNestedFrames;

public class ProblemStateLabellerFactory {

	public static final ProblemStateLabellerFactory INSTANCE = new ProblemStateLabellerFactory();

	private ProblemStateLabellerFactory() {
	}

	public ProblemStateLabeller apply(ProblemStateLabellerStrategy strategy) {
		switch (strategy) {
		case AS_NESTED_FRAMES:
			return AsNestedFrames.INSTANCE;
		default:
			return null;
		}
	}

}
