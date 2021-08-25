package com.tregouet.occam.data.categories;

import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.tree_finder.ITreeFinder;
import com.tregouet.tree_finder.data.InTree;

public interface ICatTreeSupplier extends ITreeFinder<ICategory, DefaultEdge> {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	public static InTree<ICategory, DefaultEdge> removeTunnelCategories(InTree<ICategory, DefaultEdge> categoryTree){
		List<ICategory> tunnelCategories = categoryTree.vertexSet()
				.stream()
				.filter(c -> categoryTree.inDegreeOf(c) == 1 
						&& c.type() == ICategory.SUBSET_CAT)
				.collect(Collectors.toList());
		for (ICategory tunnelCategory : tunnelCategories) {
			DefaultEdge incomingEdge = categoryTree.incomingEdgesOf(tunnelCategory).iterator().next();
			DefaultEdge outgoingEdge = categoryTree.outgoingEdgesOf(tunnelCategory).iterator().next();
			ICategory predecessor = categoryTree.getEdgeSource(incomingEdge);
			ICategory successor = categoryTree.getEdgeTarget(outgoingEdge);
			categoryTree.removeVertex(tunnelCategory);
			categoryTree.addEdge(predecessor, successor, new DefaultEdge());
		}
		return categoryTree;
	}

}
