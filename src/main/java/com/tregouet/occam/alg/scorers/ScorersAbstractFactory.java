package com.tregouet.occam.alg.scorers;

import com.tregouet.occam.alg.scorers.representations.RepresentationLexicographicScorer;
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
	
	private ScorersAbstractFactory() {
	}
	
	public void setUpStrategy(ScoringStrategy overallStrategy) {
		switch (overallStrategy) {
			case SCORING_STRATEGY_1 : 
				similarityScorerStrategy = SimilarityScorerStrategy.DYNAMIC_FRAMING;
				representationScorerStrategy = RepresentationScorerStrategy.LEXICOGRAPHIC_COMPARISON_OF_RANKS;
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
	
	public RepresentationLexicographicScorer getRepresentationLexicographicScorer(){
		return RepresentationScorerFactory.INSTANCE.apply(representationScorerStrategy);
	}

}
