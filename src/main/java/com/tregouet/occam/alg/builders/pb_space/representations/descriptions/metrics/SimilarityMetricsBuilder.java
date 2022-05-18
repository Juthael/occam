package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics;

import java.util.Map;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.similarity.AsymmetricalSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.PairSimilarityScorer;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

/**
 * 2nd parameter : context particular ID to most specific concept ID in first parameter
 * @author Gael Tregouet
 *
 */
public interface SimilarityMetricsBuilder 
	extends BiFunction<
		Tree<Integer, AbstractDifferentiae>, 
		Map<Integer, Integer>, 
		ISimilarityMetrics> {
	
	public static AsymmetricalSimilarityScorer asymmetricalSimilarityScorer() {
		return ScorersAbstractFactory.INSTANCE.getAsymmetricalSimilarityScorer();
	}

	public static PairSimilarityScorer pairSimilarityScorer() {
		return ScorersAbstractFactory.INSTANCE.getPairSimilarityScorer();
	}

}
