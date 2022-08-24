package com.tregouet.occam.alg.scorers;

import com.tregouet.occam.alg.scorers.categorizations.ProblemStateScorer;
import com.tregouet.occam.alg.scorers.categorizations.ProblemStateScorerFactory;
import com.tregouet.occam.alg.scorers.categorizations.ProblemStateScorerStrategy;
import com.tregouet.occam.alg.scorers.comparisons.difference.DifferenceScorer;
import com.tregouet.occam.alg.scorers.comparisons.difference.DifferenceScorerFactory;
import com.tregouet.occam.alg.scorers.comparisons.difference.DifferenceScorerStrategy;
import com.tregouet.occam.alg.scorers.comparisons.similarity.asymmetrical.AsymmetricalSimilarityScorer;
import com.tregouet.occam.alg.scorers.comparisons.similarity.asymmetrical.AsymmetricalSimilarityScorerFactory;
import com.tregouet.occam.alg.scorers.comparisons.similarity.asymmetrical.AsymmetricalSimilarityScorerStrategy;
import com.tregouet.occam.alg.scorers.comparisons.similarity.basic.SimilarityScorer;
import com.tregouet.occam.alg.scorers.comparisons.similarity.basic.SimilarityScorerFactory;
import com.tregouet.occam.alg.scorers.comparisons.similarity.basic.SimilarityScorerStrategy;
import com.tregouet.occam.alg.scorers.comparisons.similarity.typicality.TypicalityScorer;
import com.tregouet.occam.alg.scorers.comparisons.similarity.typicality.TypicalityScorerFactory;
import com.tregouet.occam.alg.scorers.comparisons.similarity.typicality.TypicalityScorerStrategy;

public class ScorersAbstractFactory {

	public static final ScorersAbstractFactory INSTANCE = new ScorersAbstractFactory();

	private AsymmetricalSimilarityScorerStrategy asymmetricalSimilarityScorerStrategy = null;
	private DifferenceScorerStrategy differenceScorerStrategy = null;
	private SimilarityScorerStrategy similarityScorerStrategy = null;
	private TypicalityScorerStrategy typicalityScorerStrategy = null;
	private ProblemStateScorerStrategy problemStateScorerStrategy = null;

	private ScorersAbstractFactory() {
	}

	public AsymmetricalSimilarityScorer getAsymmetricalSimilarityScorer() {
		return AsymmetricalSimilarityScorerFactory.INSTANCE.apply(asymmetricalSimilarityScorerStrategy);
	}

	public DifferenceScorer getDifferenceScorer() {
		return DifferenceScorerFactory.INSTANCE.apply(differenceScorerStrategy);
	}

	public ProblemStateScorer getProblemStateScorer() {
		return ProblemStateScorerFactory.INSTANCE.apply(problemStateScorerStrategy);
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
			differenceScorerStrategy = DifferenceScorerStrategy.DIFFERENTIAE_WEIGHTS;
			similarityScorerStrategy = SimilarityScorerStrategy.SPECIES_WEIGHT;
			typicalityScorerStrategy = TypicalityScorerStrategy.HOW_MUCH_ARE_OTHERS_SIMILAR;
		default:
			break;
		}
	}

}
