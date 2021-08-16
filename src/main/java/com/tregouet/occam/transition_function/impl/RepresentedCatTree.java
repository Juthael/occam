package com.tregouet.occam.transition_function.impl;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IExtentStructureConstraint;
import com.tregouet.occam.transition_function.IRepresentedCatTree;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.tree_finder.data.InTree;

public class RepresentedCatTree implements IRepresentedCatTree {

	private final InTree<ICategory,DefaultEdge> categoryTree;
	private ITransitionFunction optimalRepresentation = null;
	
	public RepresentedCatTree(InTree<ICategory, DefaultEdge> categoryTree) {
		this.categoryTree = categoryTree;
	}

	@Override
	public int compareTo(IRepresentedCatTree other) {
		if (this.getCost() < other.getCost())
			return -1;
		if (this.getCost() > other.getCost())
			return 1;
		return 0;
	}

	@Override
	public InTree<ICategory, DefaultEdge> getCategoryTree() {
		return categoryTree;
	}

	@Override
	public ITransitionFunction getTransitionFunction() {
		return optimalRepresentation;
	}

	@Override
	public boolean meetsConstraint(IExtentStructureConstraint constraint) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getCost() {
		return optimalRepresentation.getCost();
	}

	@Override
	public boolean testAlternativeRepresentation(ITransitionFunction altRepresentation) {
		if (optimalRepresentation == null || altRepresentation.getCost() < this.getCost()) {
			optimalRepresentation = altRepresentation;
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryTree == null) ? 0 : categoryTree.hashCode());
		return result;
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

}