package com.tregouet.occam.data.problem_space.states.descriptions;

import java.util.List;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.IRelativeSimilarityMetrics;
import com.tregouet.tree_finder.data.Tree;

public interface IDescription {

	Tree<Integer, ADifferentiae> asGraph();

	@Override
	boolean equals(Object o);

	IRelativeSimilarityMetrics getSimilarityMetrics();

	List<Integer> getTopologicallyOrderedConceptIDs();

	@Override
	int hashCode();

}
