package com.tregouet.occam.data.representations.descriptions;

import java.util.List;

import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.representations.descriptions.metrics.IRelativeSimilarityMetrics;
import com.tregouet.tree_finder.data.Tree;

public interface IDescription {

	Tree<Integer, ADifferentiae> asGraph();

	@Override
	boolean equals(Object o);

	IRelativeSimilarityMetrics getSimilarityMetricsDEP();

	List<Integer> getTopologicallyOrderedConceptIDs();

	@Override
	int hashCode();

}
