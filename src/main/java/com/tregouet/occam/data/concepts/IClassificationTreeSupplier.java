package com.tregouet.occam.data.concepts;

import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.data.Tree;

public interface IClassificationTreeSupplier extends IUnidimensionalSorter<IConcept, IsA> {
	
	public static Tree<IConcept, IsA> proceedToOntologicalCommitment(
			Tree<IConcept, IsA> classificationTree, IConcept ontologicalCommitment) {
		classificationTree.addAsNewRoot(ontologicalCommitment, true);
		return classificationTree;
	}
	
	public Tree<IConcept, IsA> nextOntologicalCommitment();

}
