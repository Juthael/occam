package com.tregouet.occam.transition_function;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IExtentStructureConstraint;
import com.tregouet.tree_finder.data.InTree;

public interface IRepresentedCatTree extends Comparable<IRepresentedCatTree> {
	
	InTree<ICategory, DefaultEdge> getCategoryTree();
	
	ITransitionFunction getTransitionFunction();
	
	boolean meetsConstraint(IExtentStructureConstraint constraint);
	
	double getCost();
	
	boolean testAlternativeRepresentation(ITransitionFunction altRepresentation);
	
	@Override
	String toString();
	
}
