package com.tregouet.occam.data.abstract_machines.automatons;

import java.util.Iterator;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IExtentStructureConstraint;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;

public interface IIsomorphicAutomatons extends Comparable<IIsomorphicAutomatons> {
	
	double getCoherenceScore();
	
	String getExtentStructureAsString();
	
	Iterator<IAutomaton> getIteratorOverAutomatons();
	
	IAutomaton getOptimalAutomaton();
	
	Tree<IConcept, IIsA> getTreeOfDenotationSets();
	
	boolean meetsConstraint(IExtentStructureConstraint constraint);
	
	boolean addIsomorphicAutomaton(IAutomaton altRepresentation);
	
}
