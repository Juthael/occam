package com.tregouet.occam.data.representations.concepts.utils;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;

import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public class TreeOfConceptsToStringConvertor {

	private final InvertedTree<IConcept, IIsA> treeOfDenotationSets;
	private final Map<IConcept, String> leafToLeafName;
	
	public TreeOfConceptsToStringConvertor(InvertedTree<IConcept, IIsA> treeOfDenotationSets, Map<IConcept, String> leafToLeafName) {
		this.treeOfDenotationSets = treeOfDenotationSets;
		this.leafToLeafName = leafToLeafName;
	}
	
	@Override
	public String toString() {
		return getExtentStructure(treeOfDenotationSets.getRoot());
	}
	
	private String getExtentStructure(IConcept concept) {
		String extentStructure;
		if (concept.type() == ConceptType.PARTICULAR)
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
