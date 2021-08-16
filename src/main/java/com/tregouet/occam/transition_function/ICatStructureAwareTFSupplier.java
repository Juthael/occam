package com.tregouet.occam.transition_function;

import java.util.Iterator;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.tree_finder.data.InTree;

public interface ICatStructureAwareTFSupplier extends ITransitionFunctionSupplier, Iterator<IRepresentedCatTree> {
	
	InTree<ICategory, DefaultEdge> getOptimalCategoryStructure();

}
