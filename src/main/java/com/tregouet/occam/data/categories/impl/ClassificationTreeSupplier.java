package com.tregouet.occam.data.categories.impl;

import java.util.Set;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IClassificationTreeSupplier;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.data.Tree;

public class ClassificationTreeSupplier implements IClassificationTreeSupplier {

	private final IUnidimensionalSorter<ICategory, IsA> categorySorter;
	private final ICategory ontologicalCommitment;
	
	public ClassificationTreeSupplier(IUnidimensionalSorter<ICategory, IsA> categorySorter, 
			ICategory ontologicalCommitment) {
		this.categorySorter = categorySorter;
		this.ontologicalCommitment = ontologicalCommitment;
	}

	@Override
	public Set<Tree<ICategory, IsA>> getSortingTrees() {
		return categorySorter.getSortingTrees();
	}

	@Override
	public boolean hasNext() {
		return categorySorter.hasNext();
	}

	@Override
	public Tree<ICategory, IsA> next() {
		return categorySorter.next();
	}

	@Override
	public Tree<ICategory, IsA> nextOntologicalCommitment() {
		return IClassificationTreeSupplier.proceedToOntologicalCommitment(categorySorter.next(), ontologicalCommitment);
	}

}
