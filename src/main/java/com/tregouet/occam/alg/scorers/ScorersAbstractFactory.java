package com.tregouet.occam.alg.scorers;

import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorerFactory;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorerStrategy;
import com.tregouet.occam.alg.scorers.similarity.asymmetrical.AsymmetricalSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.asymmetrical.AsymmetricalSimilarityScorerFactory;
import com.tregouet.occam.alg.scorers.similarity.asymmetrical.AsymmetricalSimilarityScorerStrategy;
import com.tregouet.occam.alg.scorers.similarity.difference.DifferenceScorer;
import com.tregouet.occam.alg.scorers.similarity.difference.DifferenceScorerFactory;
import com.tregouet.occam.alg.scorers.similarity.difference.DifferenceScorerStrategy;
import com.tregouet.occam.alg.scorers.similarity.symmetrical.SimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.symmetrical.SimilarityScorerFactory;
import com.tregouet.occam.alg.scorers.similarity.symmetrical.SimilarityScorerStrategy;
import com.tregouet.occam.alg.scorers.similarity.typicality.TypicalityScorer;
import com.tregouet.occam.alg.scorers.similarity.typicality.TypicalityScorerFactory;
import com.tregouet.occam.alg.scorers.similarity.typicality.TypicalityScorerStrategy;

public class ScorersAbstractFactory {

	public static final ScorersAbstractFactory INSTANCE = new ScorersAbstractFactory();

	private AsymmetricalSimilarityScorerStrategy asymmetricalSimilarityScorerStrategy = null;
	private DifferenceScorerStrategy differenceScorerStrategy = null;
	private SimilarityScorerStrategy similarityScorerStrategy = null;
	private TypicalityScorerStrategy typicalityScorerStrategy = null;
	private ProblemStateScorerStrategy problemStateScorerStrategy = null;

	private ScorersAbstractFactory() {
	}

	public ProblemStateScorer getProblemStateScorer() {
		return ProblemStateScorerFactory.INSTANCE.apply(problemStateScorerStrategy);
	}
	
	public AsymmetricalSimilarityScorer getAsymmetricalSimilarityScorer() {
		return AsymmetricalSimilarityScorerFactory.INSTANCE.apply(asymmetricalSimilarityScorerStrategy);
	}
	
	public DifferenceScorer getDifferenceScorer() {
		return DifferenceScorerFactory.INSTANCE.apply(differenceScorerStrategy);
	}
	
	public SimilarityScorer getSimilarityScorer() {
		return SimilarityScorerFactory.INSTANCE.apply(similarityScorerStrategy);
	}
	
	public TypicalityScorer getTypicalityScorer() {
		return TypicalityScorerFactory.INSTANCE.apply(typicalityScorerStrategy);
	}

	public void setUpStrategy(ScoringStrategy overallStrategy) {
		switch (overallStrategy) {
		case SCORING_STRATEGY_2 :
			problemStateScorerStrategy = ProblemStateScorerStrategy.SOURCE_PROB_TIMES_TRANSITION_PROB;
			asymmetricalSimilarityScorerStrategy = AsymmetricalSimilarityScorerStrategy.PORTION_OF_SIMILARITY;
			differenceScorerStrategy = DifferenceScorerStrategy.SUM_OF_DIFFERENTIAE_WEIGHTS;
			similarityScorerStrategy = SimilarityScorerStrategy.DIFFERENTIAE_WEIGHT_SUM;
			typicalityScorerStrategy = TypicalityScorerStrategy.HOW_MUCH_ARE_OTHERS_SIMILAR;
		default:
			break;
		}
	}

}
