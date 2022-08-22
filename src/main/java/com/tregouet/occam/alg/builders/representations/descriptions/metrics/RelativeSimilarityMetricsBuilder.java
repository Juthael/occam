package com.tregouet.occam.alg.builders.representations.descriptions.metrics;

import java.util.Map;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.similarity.RelativeAsymmetricalSimilarityScorer;
import com.tregouet.occam.alg.scorers.similarity.RelativePairSimilarityScorer;
import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.representations.descriptions.metrics.IRelativeSimilarityMetrics;
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
