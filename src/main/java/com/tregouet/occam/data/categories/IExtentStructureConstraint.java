package com.tregouet.occam.data.categories;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.tree_finder.data.InTree;

public interface IExtentStructureConstraint {
	
	public int hashCode();
	
	boolean equals(Object o);
	
	boolean metBy(InTree<ICategory, DefaultEdge> catTree);

}
