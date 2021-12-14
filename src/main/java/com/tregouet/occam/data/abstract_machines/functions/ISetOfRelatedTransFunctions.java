package com.tregouet.occam.data.abstract_machines.functions;

import java.util.Iterator;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IExtentStructureConstraint;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public interface ISetOfRelatedTransFunctions extends Comparable<ISetOfRelatedTransFunctions> {
	
	double getCoherenceScore();
	
	String getDefinitionOfObjects();
	
	String getExtentStructureAsString();
	
	Iterator<ITransitionFunction> getIteratorOverTransitionFunctions();
	
	ITransitionFunction getOptimalTransitionFunction();
	
	Tree<IConcept, IsA> getTreeOfConcepts();
	
	//has a transition function
	boolean isValid();
	
	boolean meetsConstraint(IExtentStructureConstraint constraint);
	
	boolean testAlternativeRepresentation(ITransitionFunction altRepresentation);
	
	@Override
	String toString();
	
}
