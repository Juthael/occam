package com.tregouet.occam.data.abstract_machines.automatons.impl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import com.tregouet.occam.alg.transition_function_gen.IStructureBasedTFSupplier;
import com.tregouet.occam.data.abstract_machines.automatons.IIsomorphicAutomatons;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IExtentStructureConstraint;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.denotations.utils.TreeOfDenotationSetsToStringConvertor;
import com.tregouet.tree_finder.data.Tree;

public class IsomorphicAutomatons implements IIsomorphicAutomatons {

	private final Tree<IDenotationSet,IIsA> treeOfDenotationSets;
	private final Map<IDenotationSet, String> objDenotationSetToName;
	private final Comparator<IAutomaton> functionComparator = ScoreThenCostTFComparator.INSTANCE;
	private final TreeSet<IAutomaton> automatons = new TreeSet<>(functionComparator);
	
	public IsomorphicAutomatons(Tree<IDenotationSet, IIsA> treeOfDenotationSets, 
			Map<IDenotationSet, String> objDenotationSetToName) {
		this.treeOfDenotationSets = treeOfDenotationSets;
		this.objDenotationSetToName = objDenotationSetToName;
	}

	@Override
	public int compareTo(IIsomorphicAutomatons other) {
		if (this.getCoherenceScore() > other.getCoherenceScore())
			return -1;
		if (this.getCoherenceScore() < other.getCoherenceScore())
			return 1;
		return functionComparator.compare(
				this.getOptimalTransitionFunction(), other.getOptimalTransitionFunction());
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
		IAutomaton optimalTF = getOptimalTransitionFunction();
		if (optimalTF == null) {
			if (other.getOptimalTransitionFunction() != null)
				return false;
		} else if (!optimalTF.equals(other.getOptimalTransitionFunction()))
			return false;
		return true;
	}

	@Override
	public double getCoherenceScore() {
		return getOptimalTransitionFunction().getSimilarityCalculator().getCoherenceScore();
	}

	@Override
	public String getObjectsDenotationsAsString() {
		return IStructureBasedTFSupplier.getObjectsDenotationsAsString(objDenotationSetToName);
	}

	@Override
	public String getExtentStructureAsString() {
		return new TreeOfDenotationSetsToStringConvertor(treeOfDenotationSets, objDenotationSetToName).toString();
	}

	@Override
	public Iterator<IAutomaton> getIteratorOverTransitionFunctions() {
		return automatons.iterator();
	}

	@Override
	public IAutomaton getOptimalTransitionFunction() {
		if (automatons.isEmpty())
			return null;
		else return automatons.first();
	}

	@Override
	public Tree<IDenotationSet, IIsA> getTreeOfDenotationSets() {
		return treeOfDenotationSets;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((treeOfDenotationSets == null) ? 0 : treeOfDenotationSets.hashCode());
		return result;
	}

	@Override
	public boolean isValid() {
		return !automatons.isEmpty();
	}

	@Override
	public boolean meetsConstraint(IExtentStructureConstraint constraint) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean testAlternativeRepresentation(IAutomaton altRepresentation) {
		automatons.add(altRepresentation);
		//equality check intentionally based on reference
		return automatons.first() == altRepresentation;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		String newLine = System.lineSeparator();
		sB.append(getObjectsDenotationsAsString());
		sB.append(newLine + newLine);
		sB.append("*** CATEGORY STRUCTURE : ");
		sB.append(getExtentStructureAsString());
		sB.append(newLine + newLine);
		sB.append("*** SCORE : " + Double.toString(
				getOptimalTransitionFunction().getSimilarityCalculator().getCoherenceScore()) + 
				newLine + newLine);
		return sB.toString();
	}

}
