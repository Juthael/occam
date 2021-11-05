package com.tregouet.occam.transition_function.impl;

import java.util.Map;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IExtentStructureConstraint;
import com.tregouet.occam.data.categories.utils.CatTreeToStringConvertor;
import com.tregouet.occam.transition_function.ICatStructureAwareTFSupplier;
import com.tregouet.occam.transition_function.IRepresentedCatTree;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.tree_finder.data.Tree;

public class RepresentedCatTree implements IRepresentedCatTree {

	private final Tree<ICategory,DefaultEdge> categoryTree;
	private final Map<ICategory, String> objectCategoryToName;
	private ITransitionFunction optimalRepresentation = null;
	
	public RepresentedCatTree(Tree<ICategory, DefaultEdge> categoryTree, 
			Map<ICategory, String> objectCategoryToName) {
		this.categoryTree = categoryTree;
		this.objectCategoryToName = objectCategoryToName;
	}

	@Override
	public int compareTo(IRepresentedCatTree other) {
		if (this.getCoherenceScore() > other.getCoherenceScore())
			return -1;
		if (this.getCoherenceScore() < other.getCoherenceScore())
			return 1;
		return optimalRepresentation.compareTo(other.getTransitionFunction());
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
		if (optimalRepresentation == null) {
			if (other.optimalRepresentation != null)
				return false;
		} else if (!optimalRepresentation.equals(other.optimalRepresentation))
			return false;
		return true;
	}

	@Override
	public Tree<ICategory, DefaultEdge> getCategoryTree() {
		return categoryTree;
	}

	@Override
	public double getCoherenceScore() {
		return optimalRepresentation.getCoherenceScore();
	}

	@Override
	public String getDefinitionOfObjects() {
		return ICatStructureAwareTFSupplier.getDefinitionOfObjects(objectCategoryToName);
	}

	@Override
	public String getExtentStructureAsString() {
		return new CatTreeToStringConvertor(categoryTree, objectCategoryToName).toString();
	}

	@Override
	public ITransitionFunction getTransitionFunction() {
		return optimalRepresentation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryTree == null) ? 0 : categoryTree.hashCode());
		return result;
	}
	
	@Override
	public boolean meetsConstraint(IExtentStructureConstraint constraint) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean testAlternativeRepresentation(ITransitionFunction altRepresentation) {
		if (optimalRepresentation == null 
				|| altRepresentation.getCoherenceScore() > optimalRepresentation.getCoherenceScore()) {
			optimalRepresentation = altRepresentation;
			return true;
		}
		return false;
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
		sB.append("*** SCORE : " + Double.toString(optimalRepresentation.getCoherenceScore()) + newLine + newLine);
		return sB.toString();
	}

}
