package com.tregouet.occam.data.abstract_machines.functions;

import java.util.Iterator;

import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IExtentStructureConstraint;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.Tree;

public interface IIsomorphicTransFunctions extends Comparable<IIsomorphicTransFunctions> {
	
	double getCoherenceScore();
	
	String getObjectsDenotationsAsString();
	
	String getExtentStructureAsString();
	
	Iterator<ITransitionFunction> getIteratorOverTransitionFunctions();
	
	ITransitionFunction getOptimalTransitionFunction();
	
	Tree<IDenotationSet, IIsA> getTreeOfDenotationSets();
	
	//has a transition function
	boolean isValid();
	
	boolean meetsConstraint(IExtentStructureConstraint constraint);
	
	boolean testAlternativeRepresentation(ITransitionFunction altRepresentation);
	
	@Override
	String toString();
	
}
