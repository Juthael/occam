package com.tregouet.occam.alg.builders.problem_spaces.partial_representations.metrics;

import java.util.Map;
import java.util.function.BiFunction;

import com.tregouet.occam.data.representations.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface SimilarityPartialMetricsBuilder 
	extends BiFunction<
		Tree<Integer, AbstractDifferentiae>, 
		Map<Integer, Integer>, 
		ISimilarityMetrics> {

}
