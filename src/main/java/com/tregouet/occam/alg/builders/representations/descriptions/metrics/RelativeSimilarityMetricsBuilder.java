package com.tregouet.occam.alg.builders.representations.descriptions.metrics;

import java.util.Map;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.similarity_DEP.RelativeAsymmetricalSimilarityScorerDEP;
import com.tregouet.occam.alg.scorers.similarity_DEP.RelativePairSimilarityScorerDEP;
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

	public static RelativeAsymmetricalSimilarityScorerDEP relativeAsymmetricalSimilarityScorerDEP() {
		return ScorersAbstractFactory.INSTANCE.getAsymmetricalSimilarityScorerDEP();
	}

	public static RelativePairSimilarityScorerDEP relativePairSimilarityScorerDEP() {
		return ScorersAbstractFactory.INSTANCE.getPairSimilarityScorerDEP();
	}

}
