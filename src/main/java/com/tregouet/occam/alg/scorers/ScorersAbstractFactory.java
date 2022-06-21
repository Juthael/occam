package com.tregouet.occam.alg.scorers;

import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorerFactory;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorerStrategy;
import com.tregouet.occam.alg.scorers.similarity.RelativeAsymmetricalSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.RelativePairSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.RelativeSimilarityScorerFactory;
import com.tregouet.occam.alg.scorers.similarity.RelativeSimilarityScorerStrategy;
import com.tregouet.occam.alg.scorers.similarity.RelativeSubsetSimilarityScorer;

public class ScorersAbstractFactory {

	public static final ScorersAbstractFactory INSTANCE = new ScorersAbstractFactory();

	private RelativeSimilarityScorerStrategy relativeSimilarityScorerStrategy = null;
	private ProblemStateScorerStrategy problemStateScorerStrategy = null;

	private ScorersAbstractFactory() {
	}

	public RelativeAsymmetricalSimilarityScorer getAsymmetricalSimilarityScorer() {
		return RelativeSimilarityScorerFactory.INSTANCE.getAsymmetricalSimilarityScorer(relativeSimilarityScorerStrategy);
	}

	public RelativeSubsetSimilarityScorer getBasicSimilarityScorer() {
		return RelativeSimilarityScorerFactory.INSTANCE.getBasicSimilarityScorer(relativeSimilarityScorerStrategy);
	}

	public RelativePairSimilarityScorer getPairSimilarityScorer() {
		return RelativeSimilarityScorerFactory.INSTANCE.getPairSimilarityScorer(relativeSimilarityScorerStrategy);
	}

	public ProblemStateScorer getProblemStateScorer() {
		return ProblemStateScorerFactory.INSTANCE.apply(problemStateScorerStrategy);
	}

	public void setUpStrategy(ScoringStrategy overallStrategy) {
		switch (overallStrategy) {
		case SCORING_STRATEGY_1:
			relativeSimilarityScorerStrategy = RelativeSimilarityScorerStrategy.DYNAMIC_FRAMING;
			problemStateScorerStrategy = ProblemStateScorerStrategy.SOURCE_PROB_TIMES_TRANSITION_PROB;
			break;
		default:
			break;
		}
	}

}
