package com.tregouet.occam.alg.scorers.problem_states;

import com.tregouet.occam.alg.scorers.problem_states.impl.MarkhovProcess;

public class ProblemStateScorerFactory {

	public static final ProblemStateScorerFactory INSTANCE = new ProblemStateScorerFactory();

	private ProblemStateScorerFactory() {
	}

	public ProblemStateScorer apply(ProblemStateScorerStrategy strategy) {
		switch (strategy) {
			case MARKHOV_PROCESS :
				return new MarkhovProcess();
			default :
				return null;
		}
	}

}
