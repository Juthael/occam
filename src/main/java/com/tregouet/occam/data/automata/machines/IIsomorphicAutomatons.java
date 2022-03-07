package com.tregouet.occam.data.automata.machines;

import java.util.Iterator;

import com.tregouet.occam.data.preconcepts.IExtentStructureConstraint;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.tree_finder.data.Tree;

public interface IIsomorphicAutomatons extends Comparable<IIsomorphicAutomatons> {
	
	double getCoherenceScore();
	
	String getExtentStructureAsString();
	
	Iterator<IAutomaton> getIteratorOverAutomatons();
	
	IAutomaton getOptimalAutomaton();
	
	Tree<IPreconcept, IIsA> getTreeOfDenotationSets();
	
	boolean addIsomorphicAutomaton(IAutomaton altRepresentation);
	
}
