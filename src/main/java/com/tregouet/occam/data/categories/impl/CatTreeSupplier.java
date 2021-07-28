package com.tregouet.occam.data.categories.impl;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.ICatTreeSupplier;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.tree_finder.error.InvalidSemiLatticeExeption;
import com.tregouet.tree_finder.impl.TreeFinder;

public class CatTreeSupplier extends TreeFinder<ICategory, DefaultEdge> implements ICatTreeSupplier {

	public CatTreeSupplier(DirectedAcyclicGraph<ICategory, DefaultEdge> upperSemiLattice) {
		super(upperSemiLattice);
	}

	public CatTreeSupplier(DirectedAcyclicGraph<ICategory, DefaultEdge> upperSemiLattice, boolean validateArg)
			throws InvalidSemiLatticeExeption {
		super(upperSemiLattice, validateArg);
	}

}
