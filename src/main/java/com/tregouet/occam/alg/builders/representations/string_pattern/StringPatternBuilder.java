package com.tregouet.occam.alg.builders.representations.string_pattern;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface StringPatternBuilder extends Function<Tree<Integer, AbstractDifferentiae>, String> {

	// only required if the function is applied to the graph of a partial
	// representation
	public StringPatternBuilder setUp(Map<Integer, List<Integer>> conceptID2ExtentIDs);

}