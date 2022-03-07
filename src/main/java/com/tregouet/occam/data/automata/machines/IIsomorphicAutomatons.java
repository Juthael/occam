package com.tregouet.occam.data.automata.machines;

import java.util.Iterator;

import com.tregouet.occam.data.denotations.IPreconcept;
import com.tregouet.occam.data.denotations.IExtentStructureConstraint;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.Tree;

public interface IIsomorphicAutomatons extends Comparable<IIsomorphicAutomatons> {
	
	double getCoherenceScore();
	
	String getExtentStructureAsString();
	
	Iterator<IAutomaton> getIteratorOverAutomatons();
	
	IAutomaton getOptimalAutomaton();
	
	Tree<IPreconcept, IIsA> getTreeOfDenotationSets();
	
	boolean meetsConstraint(IExtentStructureConstraint constraint);
	
	boolean addIsomorphicAutomaton(IAutomaton altRepresentation);
	
}
