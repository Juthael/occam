package com.tregouet.occam.alg.displayers.formatters.sortings;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface Sorting2StringConverter extends Function<Tree<Integer, ADifferentiae>, String> {

	// only required if the function is applied to the graph of a partial
	// representation or of a partition
	public Sorting2StringConverter setUp(Map<Integer, List<Integer>> conceptID2ExtentIDs);

}
