package com.tregouet.occam.alg.builders.representations.partitions.as_graphs.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.builders.representations.partitions.as_graphs.PartitionGraphBuilder;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class RecursiveForkExploration implements PartitionGraphBuilder {

	public static final RecursiveForkExploration INSTANCE = new RecursiveForkExploration();

	private RecursiveForkExploration() {
	}

	private static Set<Tree<Integer, AbstractDifferentiae>> convertIntoTrees(
			Set<DirectedAcyclicGraph<Integer, AbstractDifferentiae>> dagPartitions) {
		Set<Tree<Integer, AbstractDifferentiae>> partitions = new HashSet<>();
		for (DirectedAcyclicGraph<Integer, AbstractDifferentiae> dagPartition : dagPartitions) {
			Integer root = null;
			Set<Integer> leaves = new HashSet<>();
			List<Integer> topoOrder = new ArrayList<>();
			Iterator<Integer> topoIte = new TopologicalOrderIterator<>(dagPartition);
			while (topoIte.hasNext()) {
				Integer nextConceptID = topoIte.next();
				topoOrder.add(nextConceptID);
				if (dagPartition.inDegreeOf(nextConceptID) == 0)
					root = nextConceptID;
				if (dagPartition.outDegreeOf(nextConceptID) == 0)
					leaves.add(nextConceptID);
			}
			partitions.add(new Tree<>(dagPartition, root, leaves, topoOrder));
		}
		return partitions;
	}

	private static Set<DirectedAcyclicGraph<Integer, AbstractDifferentiae>> part(
			Tree<Integer, AbstractDifferentiae> tree, DirectedAcyclicGraph<Integer, AbstractDifferentiae> partedSoFar,
			Integer activeNode) {
		Set<DirectedAcyclicGraph<Integer, AbstractDifferentiae>> partedNext = new HashSet<>();
		if (tree.getLeaves().contains(activeNode))
			partedNext.add(partedSoFar);
		else {
			DirectedAcyclicGraph<Integer, AbstractDifferentiae> forked = shallowCopyOf(partedSoFar);
			Set<AbstractDifferentiae> fork = tree.outgoingEdgesOf(activeNode);
			Graphs.addAllEdges(forked, tree, fork);
			partedNext.add(forked);
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

	@Override
	public Set<Tree<Integer, AbstractDifferentiae>> apply(Tree<Integer, AbstractDifferentiae> tree) {
		DirectedAcyclicGraph<Integer, AbstractDifferentiae> partedSoFar = new DirectedAcyclicGraph<>(null, null, false);
		partedSoFar.addVertex(tree.getRoot());
		Set<DirectedAcyclicGraph<Integer, AbstractDifferentiae>> dagPartitions = part(tree, partedSoFar, tree.getRoot());
		return convertIntoTrees(dagPartitions);
	}

}
