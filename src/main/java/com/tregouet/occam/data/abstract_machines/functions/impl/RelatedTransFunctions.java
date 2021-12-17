package com.tregouet.occam.data.abstract_machines.functions.impl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import com.tregouet.occam.alg.calculators.CalculatorsAbstractFactory;
import com.tregouet.occam.alg.transition_function_gen.IConceptStructureBasedTFSupplier;
import com.tregouet.occam.data.abstract_machines.functions.ISetOfRelatedTransFunctions;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IExtentStructureConstraint;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.utils.TreeOfConceptsToStringConvertor;
import com.tregouet.tree_finder.data.Tree;

public class RelatedTransFunctions implements ISetOfRelatedTransFunctions {

	private final Tree<IConcept,IIsA> treeOfConcepts;
	private final Map<IConcept, String> singletonConceptToName;
	private final Comparator<ITransitionFunction> transFuncComparator = 
			CalculatorsAbstractFactory.INSTANCE.getTransFuncComparator();
	private final TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>(transFuncComparator);
	
	public RelatedTransFunctions(Tree<IConcept, IIsA> treeOfConcepts, 
			Map<IConcept, String> singletonConceptToName) {
		this.treeOfConcepts = treeOfConcepts;
		this.singletonConceptToName = singletonConceptToName;
	}

	@Override
	public int compareTo(ISetOfRelatedTransFunctions other) {
		if (this.getCoherenceScore() > other.getCoherenceScore())
			return -1;
		if (this.getCoherenceScore() < other.getCoherenceScore())
			return 1;
		return transFuncComparator.compare(
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
		RelatedTransFunctions other = (RelatedTransFunctions) obj;
		if (treeOfConcepts == null) {
			if (other.treeOfConcepts != null)
				return false;
		} else if (!treeOfConcepts.equals(other.treeOfConcepts))
			return false;
		ITransitionFunction optimalTF = getOptimalTransitionFunction();
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
	public String getDefinitionOfObjects() {
		return IConceptStructureBasedTFSupplier.getDefinitionOfObjects(singletonConceptToName);
	}

	@Override
	public String getExtentStructureAsString() {
		return new TreeOfConceptsToStringConvertor(treeOfConcepts, singletonConceptToName).toString();
	}

	@Override
	public Iterator<ITransitionFunction> getIteratorOverTransitionFunctions() {
		return transitionFunctions.iterator();
	}

	@Override
	public ITransitionFunction getOptimalTransitionFunction() {
		if (transitionFunctions.isEmpty())
			return null;
		else return transitionFunctions.first();
	}

	@Override
	public Tree<IConcept, IIsA> getTreeOfConcepts() {
		return treeOfConcepts;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((treeOfConcepts == null) ? 0 : treeOfConcepts.hashCode());
		return result;
	}

	@Override
	public boolean isValid() {
		return !transitionFunctions.isEmpty();
	}

	@Override
	public boolean meetsConstraint(IExtentStructureConstraint constraint) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean testAlternativeRepresentation(ITransitionFunction altRepresentation) {
		transitionFunctions.add(altRepresentation);
		//equality check intentionally based on reference
		return transitionFunctions.first() == altRepresentation;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		String newLine = System.lineSeparator();
		sB.append(getDefinitionOfObjects());
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
