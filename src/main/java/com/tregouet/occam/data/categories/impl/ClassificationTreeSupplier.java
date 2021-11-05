package com.tregouet.occam.data.categories.impl;

import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IClassificationTreeSupplier;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.data.Tree;

public class ClassificationTreeSupplier implements IClassificationTreeSupplier {

	private final IUnidimensionalSorter<ICategory, DefaultEdge> categorySorter;
	private final ICategory ontologicalCommitment;
	
	public ClassificationTreeSupplier(IUnidimensionalSorter<ICategory, DefaultEdge> categorySorter, 
			ICategory ontologicalCommitment) {
		this.categorySorter = categorySorter;
		this.ontologicalCommitment = ontologicalCommitment;
	}

	@Override
	public Set<Tree<ICategory, DefaultEdge>> getSortingTrees() {
		return categorySorter.getSortingTrees();
	}

	@Override
	public boolean hasNext() {
		return categorySorter.hasNext();
	}

	@Override
	public Tree<ICategory, DefaultEdge> next() {
		return categorySorter.next();
	}

	@Override
	public Tree<ICategory, DefaultEdge> nextOntologicalCommitment() {
		return IClassificationTreeSupplier.proceedToOntologicalCommitment(categorySorter.next(), ontologicalCommitment);
	}

}
