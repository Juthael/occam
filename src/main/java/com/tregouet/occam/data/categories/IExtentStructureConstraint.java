package com.tregouet.occam.data.categories;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.tree_finder.data.InTree;

public interface IExtentStructureConstraint {
	
	boolean metBy(InTree<ICategory, DefaultEdge> catTree);

}
