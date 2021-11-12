package com.tregouet.occam.data.categories;

import com.tregouet.occam.data.categories.impl.IsA;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.data.Tree;

public interface IClassificationTreeSupplier extends IUnidimensionalSorter<ICategory, IsA> {
	
	public static Tree<ICategory, IsA> proceedToOntologicalCommitment(
			Tree<ICategory, IsA> classificationTree, ICategory ontologicalCommitment) {
		classificationTree.addAsNewRoot(ontologicalCommitment, true);
		return classificationTree;
	}
	
	public Tree<ICategory, IsA> nextOntologicalCommitment();

}
