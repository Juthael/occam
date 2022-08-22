package com.tregouet.occam.alg.builders.representations.partitions.graphs;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

@FunctionalInterface
public interface PartitionGraphBuilder
		extends Function<Tree<Integer, ADifferentiae>, Set<Tree<Integer, ADifferentiae>>> {

}
