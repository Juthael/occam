package com.tregouet.occam.alg.scorers.categorizations;

import com.tregouet.occam.alg.scorers.categorizations.impl.MarkhovProcess;
import com.tregouet.occam.alg.scorers.categorizations.impl.SourcesProbabilitiesTimesTransitionsProbabilities;

public class ProblemStateScorerFactory {

	public static final ProblemStateScorerFactory INSTANCE = new ProblemStateScorerFactory();

	private ProblemStateScorerFactory() {
	}

	public ProblemStateScorer apply(ProblemStateScorerStrategy strategy) {
		switch (strategy) {
		case MARKHOV_PROCESS:
			return new MarkhovProcess();
		case SOURCE_PROB_TIMES_TRANSITION_PROB :
			return new SourcesProbabilitiesTimesTransitionsProbabilities();
		default:
			return null;
		}
	}

}
