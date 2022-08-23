package com.tregouet.occam.alg.scorers;

import com.tregouet.occam.alg.scorers.difference_DEP.DifferenceScorerDEP;
import com.tregouet.occam.alg.scorers.difference_DEP.DifferenceScorerFactoryDEP;
import com.tregouet.occam.alg.scorers.difference_DEP.DifferenceScorerStrategyDEP;
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
import com.tregouet.occam.alg.scorers.similarity_DEP.RelativeAsymmetricalSimilarityScorerDEP;
import com.tregouet.occam.alg.scorers.similarity_DEP.RelativePairSimilarityScorerDEP;
import com.tregouet.occam.alg.scorers.similarity_DEP.RelativeSimilarityScorerFactoryDEP;
import com.tregouet.occam.alg.scorers.similarity_DEP.RelativeSimilarityScorerStrategyDEP;
import com.tregouet.occam.alg.scorers.similarity_DEP.RelativeSubsetSimilarityScorerDEP;

public class ScorersAbstractFactory {

	public static final ScorersAbstractFactory INSTANCE = new ScorersAbstractFactory();

	private RelativeSimilarityScorerStrategyDEP relativeSimilarityScorerStrategyDEP = null;
	private DifferenceScorerStrategyDEP differenceScorerStrategyDEP = null;
	private AsymmetricalSimilarityScorerStrategy asymmetricalSimilarityScorerStrategy = null;
	private DifferenceScorerStrategy differenceScorerStrategy = null;
	private SimilarityScorerStrategy similarityScorerStrategy = null;
	private TypicalityScorerStrategy typicalityScorerStrategy = null;
	private ProblemStateScorerStrategy problemStateScorerStrategy = null;

	private ScorersAbstractFactory() {
	}

	public RelativeAsymmetricalSimilarityScorerDEP getAsymmetricalSimilarityScorerDEP() {
		return RelativeSimilarityScorerFactoryDEP.INSTANCE.getAsymmetricalSimilarityScorer(relativeSimilarityScorerStrategyDEP);
	}

	public RelativeSubsetSimilarityScorerDEP getBasicSimilarityScorerDEP() {
		return RelativeSimilarityScorerFactoryDEP.INSTANCE.getBasicSimilarityScorer(relativeSimilarityScorerStrategyDEP);
	}

	public DifferenceScorerDEP getDifferenceScorerDEP() {
		return DifferenceScorerFactoryDEP.INSTANCE.apply(differenceScorerStrategyDEP);
	}

	public RelativePairSimilarityScorerDEP getPairSimilarityScorerDEP() {
		return RelativeSimilarityScorerFactoryDEP.INSTANCE.getPairSimilarityScorer(relativeSimilarityScorerStrategyDEP);
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
		case SCORING_STRATEGY_1:
			relativeSimilarityScorerStrategyDEP = RelativeSimilarityScorerStrategyDEP.DYNAMIC_FRAMING;
			differenceScorerStrategyDEP = DifferenceScorerStrategyDEP.TWO_LEAF_TREE;
			problemStateScorerStrategy = ProblemStateScorerStrategy.SOURCE_PROB_TIMES_TRANSITION_PROB;
			break;
		case SCORING_STRATEGY_2 :
			relativeSimilarityScorerStrategyDEP = RelativeSimilarityScorerStrategyDEP.DYNAMIC_FRAMING_NO_COEFF;
			differenceScorerStrategyDEP = DifferenceScorerStrategyDEP.TWO_LEAF_TREE;
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
