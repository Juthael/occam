package com.tregouet.occam.data.categories.impl;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.ICatTreeSupplier;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.tree_finder.data.InTree;
import com.tregouet.tree_finder.error.InvalidSemiLatticeException;
import com.tregouet.tree_finder.impl.TreeFinder;

public class CatTreeSupplierDEPRECATED extends TreeFinder<ICategory, DefaultEdge> implements ICatTreeSupplier {

	public CatTreeSupplierDEPRECATED(DirectedAcyclicGraph<ICategory, DefaultEdge> upperSemiLattice) {
		super(upperSemiLattice);
	}

	public CatTreeSupplierDEPRECATED(DirectedAcyclicGraph<ICategory, DefaultEdge> upperSemiLattice, boolean validateArg)
			throws InvalidSemiLatticeException {
		super(upperSemiLattice, validateArg);
	}

	@Override
	public InTree<ICategory, DefaultEdge> nextWithTunnelCategoriesRemoved() {
		return ICatTreeSupplier.removeTunnelCategories(next());
	}

}
