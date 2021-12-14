package com.tregouet.occam.data.concepts.utils;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public class TreeOfConceptsToStringConvertor {

	private final Tree<IConcept, IsA> treeOfConcepts;
	private final Map<IConcept, String> leafToLeafName;
	
	public TreeOfConceptsToStringConvertor(Tree<IConcept, IsA> treeOfConcepts, Map<IConcept, String> leafToLeafName) {
		this.treeOfConcepts = treeOfConcepts;
		this.leafToLeafName = leafToLeafName;
	}
	
	@Override
	public String toString() {
		return getExtentStructure(treeOfConcepts.getRoot());
	}
	
	private String getExtentStructure(IConcept concept) {
		String extentStructure;
		if (concept.type() == IConcept.SINGLETON)
			extentStructure = leafToLeafName.get(concept);
		else {
			List<IConcept> predecessors = Graphs.predecessorListOf(treeOfConcepts, concept);
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
