package com.tregouet.occam.data.structures.representations.descriptions.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.structures.representations.descriptions.IDescription;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class Description implements IDescription {

	private final Tree<Integer, ADifferentiae> genusDiffGraph;

	public Description(Tree<Integer, ADifferentiae> genusDiffGraph) {
		this.genusDiffGraph = genusDiffGraph;
	}

	@Override
	public Tree<Integer, ADifferentiae> asGraph() {
		return genusDiffGraph;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Description other = (Description) obj;
		return Objects.equals(genusDiffGraph, other.genusDiffGraph);
	}

	@Override
	public List<Integer> getTopologicallyOrderedConceptIDs() {
		return new ArrayList<>(genusDiffGraph.getTopologicalOrder());
	}

	@Override
	public int hashCode() {
		return Objects.hash(genusDiffGraph);
	}

}
