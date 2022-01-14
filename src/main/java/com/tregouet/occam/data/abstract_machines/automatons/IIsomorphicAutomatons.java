package com.tregouet.occam.data.abstract_machines.automatons;

import java.util.Iterator;

import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IExtentStructureConstraint;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.Tree;

public interface IIsomorphicAutomatons extends Comparable<IIsomorphicAutomatons> {
	
	double getCoherenceScore();
	
	String getObjectsDenotationsAsString();
	
	String getExtentStructureAsString();
	
	Iterator<IAutomaton> getIteratorOverTransitionFunctions();
	
	IAutomaton getOptimalTransitionFunction();
	
	Tree<IDenotationSet, IIsA> getTreeOfDenotationSets();
	
	//has a transition function
	boolean isValid();
	
	boolean meetsConstraint(IExtentStructureConstraint constraint);
	
	boolean testAlternativeRepresentation(IAutomaton altRepresentation);
	
	@Override
	String toString();
	
}
