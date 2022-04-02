package com.tregouet.occam.alg.builders.representations.descriptions.ranks.impl;

import com.tregouet.occam.alg.builders.representations.descriptions.ranks.DifferentiaeRankSetter;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class DepthFirstRankSetter implements DifferentiaeRankSetter {

	public static final DepthFirstRankSetter INSTANCE = new DepthFirstRankSetter();
	
	private DepthFirstRankSetter() {
	}
	
	@Override
	public void accept(Tree<Integer, AbstractDifferentiae> classification) {
		setEdgeRank(classification, 0, classification.getRoot());
	}
	
	private void setEdgeRank(Tree<Integer, AbstractDifferentiae> tree, int currentRank, Integer currentNode) {
		for (AbstractDifferentiae diff : tree.outgoingEdgesOf(currentNode)) {
			diff.setRank(currentRank);
			setEdgeRank(tree, currentRank + 1, diff.getTarget());
		}
	}	

}
