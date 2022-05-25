package com.tregouet.occam.data.problem_space.states.descriptions;

import java.util.List;

import com.tregouet.occam.data.problem_space.states.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface IDescription {

	Tree<Integer, ADifferentiae> asGraph();

	@Override
	boolean equals(Object o);

	ISimilarityMetrics getSimilarityMetrics();

	List<Integer> getTopologicallyOrderedConceptIDs();

	@Override
	int hashCode();

}
