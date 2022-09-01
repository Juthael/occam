package com.tregouet.occam.data.structures.representations.descriptions;

import java.util.List;

import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface IDescription {

	Tree<Integer, ADifferentiae> asGraph();

	@Override
	boolean equals(Object o);

	List<Integer> getTopologicallyOrderedConceptIDs();

	@Override
	int hashCode();

}
