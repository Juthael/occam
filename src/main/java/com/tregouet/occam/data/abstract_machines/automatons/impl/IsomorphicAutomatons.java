package com.tregouet.occam.data.abstract_machines.automatons.impl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.IIsomorphicAutomatons;
import com.tregouet.occam.data.abstract_machines.automatons.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IExtentStructureConstraint;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.denotations.utils.TreeOfDenotationSetsToStringConvertor;
import com.tregouet.tree_finder.data.Tree;

public class IsomorphicAutomatons implements IIsomorphicAutomatons {

	private final Tree<IConcept,IIsA> treeOfDenotationSets;
	private final Map<IConcept, String> objDenotationSetToName;
	private final Comparator<IAutomaton> automatonComparator = ScoreThenCostTFComparator.INSTANCE;
	private final TreeSet<IAutomaton> automatons = new TreeSet<>(automatonComparator);
	
	public IsomorphicAutomatons(Tree<IConcept, IIsA> treeOfDenotationSets, 
			Map<IConcept, String> objDenotationSetToName) {
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
		return new TreeOfDenotationSetsToStringConvertor(treeOfDenotationSets, objDenotationSetToName).toString();
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
	public Tree<IConcept, IIsA> getTreeOfDenotationSets() {
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
