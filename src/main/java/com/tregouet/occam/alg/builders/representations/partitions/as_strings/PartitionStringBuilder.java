package com.tregouet.occam.alg.builders.representations.partitions.as_strings;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface PartitionStringBuilder 
	extends Function<Tree<Integer, AbstractDifferentiae>, String> {
	
	public PartitionStringBuilder setUp(Map<Integer, List<Integer>> conceptID2ExtentIDs);

}
