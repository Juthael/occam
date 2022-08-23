package com.tregouet.occam.alg.displayers.formatters.problem_states.impl;

import java.util.ArrayList;
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
import com.tregouet.occam.data.modules.categorization.transitions.partitions.IPartition;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class AsNestedFrames implements ProblemStateLabeller {

	private static final String nL = System.lineSeparator();

	public AsNestedFrames() {
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
		Map<Integer, List<Integer>> conceptID2ExtentIDs = representation.getClassification().mapConceptID2ExtentIDs();
		Set<IPartition> statePartitions = representation.getPartitions();
		stringPatternBldr.setUp(conceptID2ExtentIDs);
		sB.append(stringPatternBldr.apply(asTree(statePartitions)));
		return sB.toString();
	}

	private static Tree<Integer, ADifferentiae> asTree(Set<IPartition> intent) {
		DirectedAcyclicGraph<Integer, ADifferentiae> stateDag = new DirectedAcyclicGraph<>(null, null, false);
		for (IPartition partition : intent) {
			DirectedAcyclicGraph<Integer, ADifferentiae> partitionGraph = partition.asGraph();
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

}
