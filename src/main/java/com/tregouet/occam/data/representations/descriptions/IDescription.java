package com.tregouet.occam.data.representations.descriptions;

import java.util.List;

import com.tregouet.occam.data.representations.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface IDescription {
	
	Tree<Integer, AbstractDifferentiae> asGraph();
	
	ISimilarityMetrics getSimilarityMetrics();
	
	List<Integer> getTopologicallyOrderedConceptIDs();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);
	
}
