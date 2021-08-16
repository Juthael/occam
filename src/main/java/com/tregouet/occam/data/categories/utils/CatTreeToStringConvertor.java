package com.tregouet.occam.data.categories.utils;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.tree_finder.data.InTree;

public class CatTreeToStringConvertor {

	private final InTree<ICategory, DefaultEdge> categoryTree;
	private final Map<ICategory, String> leafToLeafName;
	
	public CatTreeToStringConvertor(InTree<ICategory, DefaultEdge> categoryTree, Map<ICategory, String> leafToLeafName) {
		this.categoryTree = categoryTree;
		this.leafToLeafName = leafToLeafName;
	}
	
	@Override
	public String toString() {
		return getExtentStructure(categoryTree.getRoot());
	}
	
	private String getExtentStructure(ICategory category) {
		String extentStructure;
		if (category.type() == ICategory.OBJECT)
			extentStructure = leafToLeafName.get(category);
		else {
			List<ICategory> predecessors = Graphs.predecessorListOf(categoryTree, category);
			if (predecessors.size() == 1)
				extentStructure = getExtentStructure(predecessors.get(0));
			else {
				StringBuilder sB = new StringBuilder();
				sB.append("( ");
				for (int i = 0 ; i < predecessors.size() ; i++) {
					sB.append(getExtentStructure(predecessors.get(i)));
					if (i < predecessors.size() - 1)
						sB.append(" ");
				}
				sB.append(" )");
				extentStructure = sB.toString();
			}
		}
		return extentStructure;
	}	

}
