package com.tregouet.occam.data.problem_space.states.descriptions;

import java.util.List;

import com.tregouet.occam.data.problem_space.states.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface IDescription {

	Tree<Integer, AbstractDifferentiae> asGraph();

	@Override
	boolean equals(Object o);

	ISimilarityMetrics getSimilarityMetrics();

	List<Integer> getTopologicallyOrderedConceptIDs();

	@Override
	int hashCode();

}
