package com.tregouet.occam.transition_function;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IExtentStructureConstraint;
import com.tregouet.tree_finder.data.ClassificationTree;

public interface IRepresentedCatTree extends Comparable<IRepresentedCatTree> {
	
	ClassificationTree<ICategory, DefaultEdge> getCategoryTree();
	
	double getCoherenceScore();
	
	String getDefinitionOfObjects();
	
	String getExtentStructureAsString();
	
	ITransitionFunction getTransitionFunction();
	
	boolean meetsConstraint(IExtentStructureConstraint constraint);
	
	boolean testAlternativeRepresentation(ITransitionFunction altRepresentation);
	
	@Override
	String toString();
	
}
