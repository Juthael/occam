package com.tregouet.occam.alg.displayers.formatters.problem_states;

import com.tregouet.occam.alg.displayers.formatters.problem_states.impl.AsNestedFrames;
import com.tregouet.occam.alg.displayers.formatters.problem_states.impl.AsNestedFramesWithScore;

public class ProblemStateLabellerFactory {

	public static final ProblemStateLabellerFactory INSTANCE = new ProblemStateLabellerFactory();

	private ProblemStateLabellerFactory() {
	}

	public ProblemStateLabeller apply(ProblemStateLabellerStrategy strategy) {
		switch (strategy) {
		case AS_NESTED_FRAMES :
			return new AsNestedFrames();
		case AS_NESTED_FRAMES_WITH_SCORE:
			return new AsNestedFramesWithScore();
		default:
			return null;
		}
	}

}
