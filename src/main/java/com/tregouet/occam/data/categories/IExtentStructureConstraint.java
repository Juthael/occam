package com.tregouet.occam.data.categories;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.tree_finder.data.InTree;

public interface IExtentStructureConstraint {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	boolean metBy(InTree<ICategory, DefaultEdge> catTree);

}
