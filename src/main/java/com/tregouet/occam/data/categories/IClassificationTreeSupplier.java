package com.tregouet.occam.data.categories;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.data.Tree;

public interface IClassificationTreeSupplier extends IUnidimensionalSorter<ICategory, DefaultEdge> {
	
	public static Tree<ICategory, DefaultEdge> proceedToOntologicalCommitment(
			Tree<ICategory, DefaultEdge> classificationTree, ICategory ontologicalCommitment) {
		classificationTree.addAsNewRoot(ontologicalCommitment);
		return classificationTree;
	}
	
	public Tree<ICategory, DefaultEdge> nextOntologicalCommitment();

}
