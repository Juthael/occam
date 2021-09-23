package com.tregouet.occam.data.categories;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.tree_finder.ITreeFinder;

public interface IClassificationTreeSupplier extends ITreeFinder<ICategory, DefaultEdge> {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);

}
