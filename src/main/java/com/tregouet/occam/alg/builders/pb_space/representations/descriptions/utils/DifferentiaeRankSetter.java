package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.utils;

import java.util.function.Consumer;

import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class DifferentiaeRankSetter implements Consumer<Tree<Integer, AbstractDifferentiae>> {

	public static final DifferentiaeRankSetter INSTANCE = new DifferentiaeRankSetter();

	private DifferentiaeRankSetter() {
	}

	/**
	 * Root is ontological commitment ID
	 */
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
