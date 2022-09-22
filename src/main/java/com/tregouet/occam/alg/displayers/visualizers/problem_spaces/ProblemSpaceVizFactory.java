package com.tregouet.occam.alg.displayers.visualizers.problem_spaces;

import com.tregouet.occam.alg.displayers.visualizers.problem_spaces.impl.BasicProblemSpaceViz;
import com.tregouet.occam.alg.displayers.visualizers.problem_spaces.impl.ProblemVizFallback;

public class ProblemSpaceVizFactory {

	public static final ProblemSpaceVizFactory INSTANCE = new ProblemSpaceVizFactory();

	private ProblemSpaceVizFactory() {
	}

	public ProblemSpaceViz apply(ProblemSpaceVizStrategy strategy) {
		switch (strategy) {
		case BASIC:
			return BasicProblemSpaceViz.INSTANCE;
		case FALLBACK :
			return ProblemVizFallback.INSTANCE;
		default:
			return null;
		}
	}

}
