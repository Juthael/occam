package com.tregouet.occam.alg.builders.representations.partitions.as_graphs;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

@FunctionalInterface
public interface PartitionGraphBuilder
		extends Function<Tree<Integer, AbstractDifferentiae>, Set<Tree<Integer, AbstractDifferentiae>>> {

}
