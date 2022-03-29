package com.tregouet.occam.alg.builders.representations.descriptions.metrics;

import java.util.function.Function;

import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.similarity.AsymmetricalSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.PairSimilarityScorer;
import com.tregouet.occam.data.representations.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface SimilarityMetricsBuilder extends Function<Tree<Integer, AbstractDifferentiae>, ISimilarityMetrics> {
	
	public static PairSimilarityScorer pairSimilarityScorer() {
		return ScorersAbstractFactory.INSTANCE.getPairSimilarityScorer();
	}
	
	public static AsymmetricalSimilarityScorer asymmetricalSimilarityScorer() {
		return ScorersAbstractFactory.INSTANCE.getAsymmetricalSimilarityScorer();
	}

}
