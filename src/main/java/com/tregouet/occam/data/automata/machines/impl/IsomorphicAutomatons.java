package com.tregouet.occam.data.automata.machines.impl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import com.tregouet.occam.data.automata.machines.IAutomaton;
import com.tregouet.occam.data.automata.machines.IIsomorphicAutomatons;
import com.tregouet.occam.data.automata.machines.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.preconcepts.IExtentStructureConstraint;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.utils.TreeOfPreconceptsToStringConvertor;
import com.tregouet.tree_finder.data.Tree;

public class IsomorphicAutomatons implements IIsomorphicAutomatons {

	private final Tree<IPreconcept,IIsA> treeOfDenotationSets;
	private final Map<IPreconcept, String> objDenotationSetToName;
	private final Comparator<IAutomaton> automatonComparator = ScoreThenCostTFComparator.INSTANCE;
	private final TreeSet<IAutomaton> automatons = new TreeSet<>(automatonComparator);
	
	public IsomorphicAutomatons(Tree<IPreconcept, IIsA> treeOfDenotationSets, 
			Map<IPreconcept, String> objDenotationSetToName) {
		this.treeOfDenotationSets = treeOfDenotationSets;
		this.objDenotationSetToName = objDenotationSetToName;
	}

	@Override
	public int compareTo(IIsomorphicAutomatons other) {
		if (this.getCoherenceScore() > other.getCoherenceScore())
			return -1;
		if (this.getCoherenceScore() < other.getCoherenceScore())
			return 1;
		return automatonComparator.compare(
				this.getOptimalAutomaton(), other.getOptimalAutomaton());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IsomorphicAutomatons other = (IsomorphicAutomatons) obj;
		if (treeOfDenotationSets == null) {
			if (other.treeOfDenotationSets != null)
				return false;
		} else if (!treeOfDenotationSets.equals(other.treeOfDenotationSets))
			return false;
		IAutomaton optimalTF = getOptimalAutomaton();
		if (optimalTF == null) {
			if (other.getOptimalAutomaton() != null)
				return false;
		} else if (!optimalTF.equals(other.getOptimalAutomaton()))
			return false;
		return true;
	}

	@Override
	public double getCoherenceScore() {
		// NOT IMPLEMENTED YET
		return 0.0;
	}

	@Override
	public String getExtentStructureAsString() {
		return new TreeOfPreconceptsToStringConvertor(treeOfDenotationSets, objDenotationSetToName).toString();
	}

	@Override
	public Iterator<IAutomaton> getIteratorOverAutomatons() {
		return automatons.iterator();
	}

	@Override
	public IAutomaton getOptimalAutomaton() {
		if (automatons.isEmpty())
			return null;
		else return automatons.first();
	}

	@Override
	public Tree<IPreconcept, IIsA> getTreeOfDenotationSets() {
		return treeOfDenotationSets;
	}

	@Override
	public boolean meetsConstraint(IExtentStructureConstraint constraint) {
		// NOT IMPLEMENTED YET
		return false;
	}

	@Override
	public boolean addIsomorphicAutomaton(IAutomaton altRepresentation) {
		automatons.add(altRepresentation);
		//equality check intentionally based on reference
		return automatons.first() == altRepresentation;
	}

}
