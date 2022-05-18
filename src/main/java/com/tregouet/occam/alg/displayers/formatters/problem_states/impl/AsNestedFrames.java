package com.tregouet.occam.alg.displayers.formatters.problem_states.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.displayers.formatters.problem_states.ProblemStateLabeller;
import com.tregouet.occam.alg.displayers.formatters.sortings.Sorting2StringConverter;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;
import com.tregouet.tree_finder.data.Tree;

public class AsNestedFrames implements ProblemStateLabeller {
	
	private static final String nL = System.lineSeparator();
	
	public AsNestedFrames() {
	}

	private static Tree<Integer, AbstractDifferentiae> asTree(Set<IPartition> intent) {
		DirectedAcyclicGraph<Integer, AbstractDifferentiae> stateDag = new DirectedAcyclicGraph<>(null, null, false);
		for (IPartition partition : intent) {
			DirectedAcyclicGraph<Integer, AbstractDifferentiae> partitionGraph = partition.asGraph();
			Graphs.addAllVertices(stateDag, partitionGraph.vertexSet());
			Graphs.addAllEdges(stateDag, partitionGraph, partitionGraph.edgeSet());
		}
		Integer root = null;
		Set<Integer> leaves = new HashSet<>();
		List<Integer> topoOrder = new ArrayList<>();
		Iterator<Integer> topoIte = new TopologicalOrderIterator<>(stateDag);
		while (topoIte.hasNext()) {
			Integer nextID = topoIte.next();
			if (root == null)
				root = nextID;
			if (stateDag.outDegreeOf(nextID) == 0)
				leaves.add(nextID);
			topoOrder.add(nextID);
		}
		return new Tree<>(stateDag, root, leaves, topoOrder);
	}

	private static List<IPartition> getMaxPartitions(Set<IPartition> partitions) {
		List<IPartition> maxPartitions = new ArrayList<>();
		Iterator<IPartition> partIte = partitions.iterator();
		if (partIte.hasNext())
			maxPartitions.add(partIte.next());
		while (partIte.hasNext()) {
			IPartition nextPart = partIte.next();
			boolean isMaximal = true;
			for (int i = 0; i < maxPartitions.size(); i++) {
				Integer comparison = nextPart.compareTo(maxPartitions.get(i));
				if (comparison != null) {
					if (comparison > 0) {
						maxPartitions.remove(i--);
					} else {
						isMaximal = false;
						break;
					}
				}
			}
			if (isMaximal)
				maxPartitions.add(nextPart);
		}
		return maxPartitions;
	}

	@Override
	public String apply(IRepresentation representation) {
		StringBuilder sB = new StringBuilder();
		sB.append(Integer.toString(representation.iD()) + nL);
		Sorting2StringConverter stringPatternBldr = ProblemStateLabeller.getSorting2StringConverter();
		if (representation.isFullyDeveloped()) {
			sB.append(stringPatternBldr.apply(representation.getDescription().asGraph()));
			return sB.toString();
		} 
		Map<Integer, List<Integer>> conceptID2ExtentIDs = new HashMap<>();
		Set<IPartition> statePartitions = representation.getPartitions();
		for (IPartition maxPart : getMaxPartitions(statePartitions))
			conceptID2ExtentIDs.putAll(maxPart.getLeaf2ExtentMap());
		stringPatternBldr.setUp(conceptID2ExtentIDs);
		sB.append(stringPatternBldr.apply(asTree(statePartitions)));
		return sB.toString();
	}

}
