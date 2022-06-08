package com.tregouet.occam.data.problem_space.states.descriptions.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.tree_finder.data.Tree;

public class Description implements IDescription {

	private final Tree<Integer, ADifferentiae> classification;
	private ISimilarityMetrics similarityMetrics = null;

	public Description(Tree<Integer, ADifferentiae> classification, ISimilarityMetrics similarityMetrics) {
		this.classification = classification;
		this.similarityMetrics = similarityMetrics;
	}

	@Override
	public Tree<Integer, ADifferentiae> asGraph() {
		return classification;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Description other = (Description) obj;
		return Objects.equals(classification, other.classification);
	}

	@Override
	public ISimilarityMetrics getSimilarityMetrics() {
		return similarityMetrics;
	}

	@Override
	public List<Integer> getTopologicallyOrderedConceptIDs() {
		return new ArrayList<>(classification.getTopologicalOrder());
	}

	@Override
	public int hashCode() {
		return Objects.hash(classification);
	}

}
