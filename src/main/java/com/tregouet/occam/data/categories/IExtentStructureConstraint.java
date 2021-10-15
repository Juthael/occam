package com.tregouet.occam.data.categories;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.tree_finder.data.ClassificationTree;

public interface IExtentStructureConstraint {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	boolean metBy(ClassificationTree<ICategory, DefaultEdge> catTree);

}
