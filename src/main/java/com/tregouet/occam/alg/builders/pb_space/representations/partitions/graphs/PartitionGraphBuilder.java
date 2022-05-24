package com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.problem_space.states.descriptions.properties.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

@FunctionalInterface
public interface PartitionGraphBuilder
		extends Function<Tree<Integer, ADifferentiae>, Set<Tree<Integer, ADifferentiae>>> {

}
