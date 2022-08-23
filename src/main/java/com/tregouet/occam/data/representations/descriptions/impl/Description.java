package com.tregouet.occam.data.representations.descriptions.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.representations.descriptions.metrics.IRelativeSimilarityMetrics;
import com.tregouet.tree_finder.data.Tree;

public class Description implements IDescription {

	private final Tree<Integer, ADifferentiae> classification;
	private IRelativeSimilarityMetrics relativeSimilarityMetrics = null;

	public Description(Tree<Integer, ADifferentiae> classification, IRelativeSimilarityMetrics relativeSimilarityMetrics) {
		this.classification = classification;
		this.relativeSimilarityMetrics = relativeSimilarityMetrics;
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
	public IRelativeSimilarityMetrics getSimilarityMetricsDEP() {
		return relativeSimilarityMetrics;
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
