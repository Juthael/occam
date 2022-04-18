package com.tregouet.occam.alg.displayers.graph_visualizers.problem_spaces;

import com.tregouet.occam.alg.displayers.graph_visualizers.problem_spaces.impl.BasicProblemSpaceViz;

public class ProblemSpaceVizFactory {

	public static final ProblemSpaceVizFactory INSTANCE = new ProblemSpaceVizFactory();

	private ProblemSpaceVizFactory() {
	}

	public ProblemSpaceViz apply(ProblemSpaceVizStrategy strategy) {
		switch (strategy) {
			case BASIC :
				return BasicProblemSpaceViz.INSTANCE;
			default :
				return null;
		}
	}

}
