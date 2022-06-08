package com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs.PartitionGraphBuilder;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class RecursiveForkExploration implements PartitionGraphBuilder {

	public static final RecursiveForkExploration INSTANCE = new RecursiveForkExploration();

	private RecursiveForkExploration() {
	}

	private static Set<Tree<Integer, ADifferentiae>> convertIntoTrees(
			Set<DirectedAcyclicGraph<Integer, ADifferentiae>> dagPartitions) {
		Set<Tree<Integer, ADifferentiae>> partitions = new HashSet<>();
		for (DirectedAcyclicGraph<Integer, ADifferentiae> dagPartition : dagPartitions) {
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

	private static Set<DirectedAcyclicGraph<Integer, ADifferentiae>> part(
			Tree<Integer, ADifferentiae> tree, DirectedAcyclicGraph<Integer, ADifferentiae> partedSoFar,
			Integer activeNode) {
		Set<DirectedAcyclicGraph<Integer, ADifferentiae>> partedNext = new HashSet<>();
		if (tree.getLeaves().contains(activeNode))
			partedNext.add(partedSoFar);
		else {
			DirectedAcyclicGraph<Integer, ADifferentiae> forked = shallowCopyOf(partedSoFar);
			Set<ADifferentiae> fork = tree.outgoingEdgesOf(activeNode);
			Graphs.addAllEdges(forked, tree, fork);
			partedNext.add(forked);
			for (ADifferentiae path : fork) {
				partedNext.addAll(part(tree, forked, path.getTarget()));
			}
		}
		return partedNext;
	}

	private static DirectedAcyclicGraph<Integer, ADifferentiae> shallowCopyOf(
			DirectedAcyclicGraph<Integer, ADifferentiae> copied) {
		DirectedAcyclicGraph<Integer, ADifferentiae> copy = new DirectedAcyclicGraph<>(null, null, false);
		Graphs.addAllVertices(copy, copied.vertexSet());
		Graphs.addAllEdges(copy, copied, copied.edgeSet());
		return copy;
	}

	@Override
	public Set<Tree<Integer, ADifferentiae>> apply(Tree<Integer, ADifferentiae> tree) {
		DirectedAcyclicGraph<Integer, ADifferentiae> partedSoFar = new DirectedAcyclicGraph<>(null, null, false);
		partedSoFar.addVertex(tree.getRoot());
		Set<DirectedAcyclicGraph<Integer, ADifferentiae>> dagPartitions = part(tree, partedSoFar, tree.getRoot());
		return convertIntoTrees(dagPartitions);
	}

}
