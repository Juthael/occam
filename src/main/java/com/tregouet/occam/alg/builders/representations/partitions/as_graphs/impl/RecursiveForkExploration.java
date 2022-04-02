package com.tregouet.occam.alg.builders.representations.partitions.as_graphs.impl;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.representations.partitions.as_graphs.PartitionGraphBuilder;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class RecursiveForkExploration implements PartitionGraphBuilder {

	public static final RecursiveForkExploration INSTANCE = new RecursiveForkExploration();
	
	private RecursiveForkExploration() {
	}
	
	@Override
	public Set<DirectedAcyclicGraph<Integer, AbstractDifferentiae>> apply(Tree<Integer, AbstractDifferentiae> tree) {
		DirectedAcyclicGraph<Integer, AbstractDifferentiae> builtSoFar = new DirectedAcyclicGraph<>(null, null, false);
		builtSoFar.addVertex(tree.getRoot());
		return part(tree, builtSoFar, tree.getRoot());
	}
	
	private static  Set<DirectedAcyclicGraph<Integer, AbstractDifferentiae>> part(
			Tree<Integer, AbstractDifferentiae> tree,
			DirectedAcyclicGraph<Integer, AbstractDifferentiae> partedSoFar, Integer activeNode) {
		Set<DirectedAcyclicGraph<Integer, AbstractDifferentiae>> partedNext = new HashSet<>();
		if (tree.getLeaves().contains(activeNode))
			partedNext.add(partedSoFar);
		else {
			DirectedAcyclicGraph<Integer, AbstractDifferentiae> forked = shallowCopyOf(partedSoFar);
			Set<AbstractDifferentiae> fork = tree.outgoingEdgesOf(activeNode);
			Graphs.addAllEdges(forked, tree, fork);
			for (AbstractDifferentiae path : fork) {
				partedNext.addAll(part(tree, forked, path.getTarget()));
			}
		}
		return partedNext;
	}
	
	private static DirectedAcyclicGraph<Integer, AbstractDifferentiae> shallowCopyOf(
			DirectedAcyclicGraph<Integer, AbstractDifferentiae> copied) {
		DirectedAcyclicGraph<Integer, AbstractDifferentiae> copy = new DirectedAcyclicGraph<>(null, null, false);
		Graphs.addAllVertices(copy, copied.vertexSet());
		Graphs.addAllEdges(copy, copied, copied.edgeSet());
		return copy;
	}	

}
