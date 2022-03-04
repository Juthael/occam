package com.tregouet.occam.data.denotations.utils;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;

import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.Tree;

public class TreeOfDenotationSetsToStringConvertor {

	private final Tree<IConcept, IIsA> treeOfDenotationSets;
	private final Map<IConcept, String> leafToLeafName;
	
	public TreeOfDenotationSetsToStringConvertor(Tree<IConcept, IIsA> treeOfDenotationSets, Map<IConcept, String> leafToLeafName) {
		this.treeOfDenotationSets = treeOfDenotationSets;
		this.leafToLeafName = leafToLeafName;
	}
	
	@Override
	public String toString() {
		return getExtentStructure(treeOfDenotationSets.getRoot());
	}
	
	private String getExtentStructure(IConcept concept) {
		String extentStructure;
		if (concept.type() == IConcept.OBJECT)
			extentStructure = leafToLeafName.get(concept);
		else {
			List<IConcept> predecessors = Graphs.predecessorListOf(treeOfDenotationSets, concept);
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
