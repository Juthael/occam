package com.tregouet.occam.transition_function;

import java.util.Iterator;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IExtentStructureConstraint;
import com.tregouet.occam.data.categories.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public interface IRepresentedCatTree extends Comparable<IRepresentedCatTree> {
	
	Tree<ICategory, IsA> getCategoryTree();
	
	double getCoherenceScore();
	
	String getDefinitionOfObjects();
	
	String getExtentStructureAsString();
	
	Iterator<ITransitionFunction> getIteratorOverTransitionFunctions();
	
	ITransitionFunction getOptimalTransitionFunction();
	
	boolean meetsConstraint(IExtentStructureConstraint constraint);
	
	boolean testAlternativeRepresentation(ITransitionFunction altRepresentation);
	
	@Override
	String toString();
	
}
