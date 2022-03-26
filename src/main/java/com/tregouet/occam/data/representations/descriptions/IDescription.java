package com.tregouet.occam.data.representations.descriptions;

import java.util.List;

import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface IDescription {
	
	Tree<Integer, AbstractDifferentiae> asGraph();
	
	double[][] getSimilarityMatrix();
	
	double[][] getAsymmetricalSimilarityMatrix();
	
	List<Integer> getTopologicallyOrderedConceptIDs();
	
}
