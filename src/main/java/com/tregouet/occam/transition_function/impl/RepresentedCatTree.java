package com.tregouet.occam.transition_function.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import com.tregouet.occam.alg.transition_function_gen.IConceptStructureBasedTFSupplier;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.concepts.utils.CatTreeToStringConvertor;
import com.tregouet.occam.transition_function.IExtentStructureConstraint;
import com.tregouet.occam.transition_function.IRepresentedCatTree;
import com.tregouet.tree_finder.data.Tree;

public class RepresentedCatTree implements IRepresentedCatTree {

	private final Tree<IConcept,IsA> categoryTree;
	private final Map<IConcept, String> objectCategoryToName;
	private final TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();
	
	public RepresentedCatTree(Tree<IConcept, IsA> categoryTree, 
			Map<IConcept, String> objectCategoryToName) {
		this.categoryTree = categoryTree;
		this.objectCategoryToName = objectCategoryToName;
	}

	@Override
	public int compareTo(IRepresentedCatTree other) {
		if (this.getCoherenceScore() > other.getCoherenceScore())
			return -1;
		if (this.getCoherenceScore() < other.getCoherenceScore())
			return 1;
		return getOptimalTransitionFunction().compareTo(other.getOptimalTransitionFunction());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RepresentedCatTree other = (RepresentedCatTree) obj;
		if (categoryTree == null) {
			if (other.categoryTree != null)
				return false;
		} else if (!categoryTree.equals(other.categoryTree))
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
	public Tree<IConcept, IsA> getCategoryTree() {
		return categoryTree;
	}

	@Override
	public double getCoherenceScore() {
		return getOptimalTransitionFunction().getCoherenceScore();
	}

	@Override
	public String getDefinitionOfObjects() {
		return IConceptStructureBasedTFSupplier.getDefinitionOfObjects(objectCategoryToName);
	}

	@Override
	public String getExtentStructureAsString() {
		return new CatTreeToStringConvertor(categoryTree, objectCategoryToName).toString();
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryTree == null) ? 0 : categoryTree.hashCode());
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
		sB.append("*** SCORE : " + Double.toString(getOptimalTransitionFunction().getCoherenceScore()) + 
				newLine + newLine);
		return sB.toString();
	}

}
