package com.tregouet.occam.alg.scorers;

import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorerFactory;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorerStrategy;
import com.tregouet.occam.alg.scorers.representations.RepresentationScorer;
import com.tregouet.occam.alg.scorers.representations.RepresentationScorerFactory;
import com.tregouet.occam.alg.scorers.representations.RepresentationScorerStrategy;
import com.tregouet.occam.alg.scorers.similarity.AsymmetricalSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.PairSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.SimilarityScorerFactory;
import com.tregouet.occam.alg.scorers.similarity.SimilarityScorerStrategy;
import com.tregouet.occam.alg.scorers.similarity.SubsetSimilarityScorer;

public class ScorersAbstractFactory {
	
	public static final ScorersAbstractFactory INSTANCE = new ScorersAbstractFactory();
	
	private SimilarityScorerStrategy similarityScorerStrategy = null;
	private RepresentationScorerStrategy representationScorerStrategy = null;
	private ProblemStateScorerStrategy problemStateScorerStrategy = null;
	
	private ScorersAbstractFactory() {
	}
	
	public void setUpStrategy(ScoringStrategy overallStrategy) {
		switch (overallStrategy) {
			case SCORING_STRATEGY_1 : 
				similarityScorerStrategy = SimilarityScorerStrategy.DYNAMIC_FRAMING;
				representationScorerStrategy = RepresentationScorerStrategy.LECTIC_COMPARISON_OF_RANKS;
				problemStateScorerStrategy = ProblemStateScorerStrategy.MARKHOV_PROCESS;
				break;
			default : 
				break;
		}
	}
	
	public SubsetSimilarityScorer getBasicSimilarityScorer() {
		return SimilarityScorerFactory.INSTANCE.getBasicSimilarityScorer(similarityScorerStrategy);
	}
	
	public AsymmetricalSimilarityScorer getAsymmetricalSimilarityScorer() {
		return SimilarityScorerFactory.INSTANCE.getAsymmetricalSimilarityScorer(similarityScorerStrategy);
	}
	
	public PairSimilarityScorer getPairSimilarityScorer() {
		return SimilarityScorerFactory.INSTANCE.getPairSimilarityScorer(similarityScorerStrategy);
	}
	
	public RepresentationScorer getRepresentationScorer() {
		return RepresentationScorerFactory.INSTANCE.apply(representationScorerStrategy);
	}
	
	public ProblemStateScorer getProblemStateScorer() {
		return ProblemStateScorerFactory.INSTANCE.apply(problemStateScorerStrategy);
	}

}
