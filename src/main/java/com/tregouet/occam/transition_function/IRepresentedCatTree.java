package com.tregouet.occam.transition_function;

import java.util.Iterator;

import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public interface IRepresentedCatTree extends Comparable<IRepresentedCatTree> {
	
	Tree<IConcept, IsA> getCategoryTree();
	
	double getCoherenceScore();
	
	String getDefinitionOfObjects();
	
	String getExtentStructureAsString();
	
	Iterator<ITransitionFunction> getIteratorOverTransitionFunctions();
	
	ITransitionFunction getOptimalTransitionFunction();
	
	//has a transition function
	boolean isValid();
	
	boolean meetsConstraint(IExtentStructureConstraint constraint);
	
	boolean testAlternativeRepresentation(ITransitionFunction altRepresentation);
	
	@Override
	String toString();
	
}
