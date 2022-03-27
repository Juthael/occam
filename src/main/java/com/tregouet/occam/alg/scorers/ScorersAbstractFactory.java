package com.tregouet.occam.alg.scorers;

import com.tregouet.occam.alg.scorers.similarity.AsymmetricalSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.BasicSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.SimilarityScorerFactory;
import com.tregouet.occam.alg.scorers.similarity.SimilarityScorerStrategy;

public class ScorersAbstractFactory {
	
	public static final ScorersAbstractFactory INSTANCE = new ScorersAbstractFactory();
	
	private SimilarityScorerStrategy similarityScorerStrategy = null;
	
	private ScorersAbstractFactory() {
	}
	
	public void setUpStrategy(ScoringStrategy overallStrategy) {
		switch (overallStrategy) {
			case SCORING_STRATEGY_1 : 
				similarityScorerStrategy = SimilarityScorerStrategy.DYNAMIC_FRAMING;
				break;
			default : 
				break;
		}
	}
	
	public BasicSimilarityScorer getBasicSimilarityScorer() {
		return SimilarityScorerFactory.INSTANCE.getBasicSimilarityScorer(similarityScorerStrategy);
	}
	
	public AsymmetricalSimilarityScorer getAsymmetricalSimilarityScorer() {
		return SimilarityScorerFactory.INSTANCE.getAsymmetricalSimilarityScorer(similarityScorerStrategy);
	}

}
