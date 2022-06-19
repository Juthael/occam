package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics;

import java.util.Map;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.similarity.relative.RelativeAsymmetricalSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.relative.RelativePairSimilarityScorer;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.IRelativeSimilarityMetrics;
import com.tregouet.tree_finder.data.Tree;

/**
 * 2nd parameter : context particular ID to most specific concept ID in first parameter
 * @author Gael Tregouet
 *
 */
public interface RelativeSimilarityMetricsBuilder
	extends BiFunction<
		Tree<Integer, ADifferentiae>,
		Map<Integer, Integer>,
		IRelativeSimilarityMetrics> {

	public static RelativeAsymmetricalSimilarityScorer relativeAsymmetricalSimilarityScorer() {
		return ScorersAbstractFactory.INSTANCE.getAsymmetricalSimilarityScorer();
	}

	public static RelativePairSimilarityScorer relativePairSimilarityScorer() {
		return ScorersAbstractFactory.INSTANCE.getPairSimilarityScorer();
	}

}
