package com.tregouet.occam.data.denotations.utils;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;

import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.Tree;

public class TreeOfDenotationSetsToStringConvertor {

	private final Tree<IDenotationSet, IIsA> treeOfDenotationSets;
	private final Map<IDenotationSet, String> leafToLeafName;
	
	public TreeOfDenotationSetsToStringConvertor(Tree<IDenotationSet, IIsA> treeOfDenotationSets, Map<IDenotationSet, String> leafToLeafName) {
		this.treeOfDenotationSets = treeOfDenotationSets;
		this.leafToLeafName = leafToLeafName;
	}
	
	@Override
	public String toString() {
		return getExtentStructure(treeOfDenotationSets.getRoot());
	}
	
	private String getExtentStructure(IDenotationSet denotationSet) {
		String extentStructure;
		if (denotationSet.type() == IDenotationSet.OBJECT)
			extentStructure = leafToLeafName.get(denotationSet);
		else {
			List<IDenotationSet> predecessors = Graphs.predecessorListOf(treeOfDenotationSets, denotationSet);
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
