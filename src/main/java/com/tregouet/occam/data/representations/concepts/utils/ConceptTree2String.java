package com.tregouet.occam.data.representations.concepts.utils;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;

import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public class ConceptTree2String {

	private final InvertedTree<IConcept, IIsA> conceptTree;
	private final Map<IConcept, String> leafToLeafName;

	public ConceptTree2String(InvertedTree<IConcept, IIsA> treeOfDenotationSets, Map<IConcept, String> leafToLeafName) {
		this.conceptTree = treeOfDenotationSets;
		this.leafToLeafName = leafToLeafName;
	}

	private String getExtentStructure(IConcept concept) {
		String extentStructure;
		if (concept.type() == ConceptType.PARTICULAR)
			extentStructure = leafToLeafName.get(concept);
		else {
			List<IConcept> predecessors = Graphs.predecessorListOf(conceptTree, concept);
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

	@Override
	public String toString() {
		return getExtentStructure(conceptTree.getRoot());
	}

}
