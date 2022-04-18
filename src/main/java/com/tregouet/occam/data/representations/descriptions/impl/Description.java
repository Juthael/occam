package com.tregouet.occam.data.representations.descriptions.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class Description implements IDescription {

	private final Tree<Integer, AbstractDifferentiae> classification;
	private ISimilarityMetrics similarityMetrics = null;

	public Description(Tree<Integer, AbstractDifferentiae> classification, ISimilarityMetrics similarityMetrics) {
		this.classification = classification;
		this.similarityMetrics = similarityMetrics;
	}

	@Override
	public Tree<Integer, AbstractDifferentiae> asGraph() {
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
