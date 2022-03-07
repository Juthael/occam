package com.tregouet.occam.data.preconcepts.utils;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;

import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.tree_finder.data.Tree;

public class TreeOfPreconceptsToStringConvertor {

	private final Tree<IPreconcept, IIsA> treeOfDenotationSets;
	private final Map<IPreconcept, String> leafToLeafName;
	
	public TreeOfPreconceptsToStringConvertor(Tree<IPreconcept, IIsA> treeOfDenotationSets, Map<IPreconcept, String> leafToLeafName) {
		this.treeOfDenotationSets = treeOfDenotationSets;
		this.leafToLeafName = leafToLeafName;
	}
	
	@Override
	public String toString() {
		return getExtentStructure(treeOfDenotationSets.getRoot());
	}
	
	private String getExtentStructure(IPreconcept preconcept) {
		String extentStructure;
		if (preconcept.type() == IPreconcept.OBJECT)
			extentStructure = leafToLeafName.get(preconcept);
		else {
			List<IPreconcept> predecessors = Graphs.predecessorListOf(treeOfDenotationSets, preconcept);
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
