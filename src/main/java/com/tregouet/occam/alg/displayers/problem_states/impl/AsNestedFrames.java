package com.tregouet.occam.alg.displayers.problem_states.impl;

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

import com.tregouet.occam.alg.builders.problem_spaces.ProblemSpaceBuilder;
import com.tregouet.occam.alg.builders.representations.string_scheme.StringSchemeBuilder;
import com.tregouet.occam.alg.displayers.problem_states.ProblemStateDisplayer;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.IPartialRepresentation;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class AsNestedFrames implements ProblemStateDisplayer {
	
	public static final AsNestedFrames INSTANCE = new AsNestedFrames();
	private static final String nL = System.lineSeparator();
	
	private AsNestedFrames() {
	}

	@Override
	public String apply(IProblemState problemState) {
		StringSchemeBuilder stringBldr = ProblemSpaceBuilder.getStringSchemeBuilder();
		if (problemState instanceof IPartialRepresentation) {
			Map<Integer, List<Integer>> conceptID2ExtentIDs = new HashMap<>();
			Set<IPartition> statePartitions = problemState.getPartitions();
			for (IPartition maxPart : getMaxPartitions(statePartitions))
				conceptID2ExtentIDs.putAll(maxPart.getLeaf2ExtentMap());
			stringBldr.setUp(conceptID2ExtentIDs);
			return stringBldr.apply(asTree(statePartitions)) + nL + problemState.score().toString();
		}
		else if (problemState instanceof ICompleteRepresentation) {
			ICompleteRepresentation completeRep = (ICompleteRepresentation) problemState;
			return stringBldr.apply(completeRep.getDescription().asGraph()) + nL + problemState.score().toString();
		}
		else // there is nothing else
			return problemState.toString() + nL + problemState.score().toString();
	}
	
	private static Tree<Integer, AbstractDifferentiae> asTree(Set<IPartition> intent) {
		DirectedAcyclicGraph<Integer, AbstractDifferentiae> stateDag = new DirectedAcyclicGraph<>(null, null, false);
		for (IPartition partition : intent) {
			DirectedAcyclicGraph<Integer, AbstractDifferentiae> partitionGraph = partition.asGraph();
			//uses addEdgeWithVertices(), so no need of vertex addition
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
		return new Tree<Integer, AbstractDifferentiae>(stateDag, root, leaves, topoOrder);
	}
	
	private static List<IPartition> getMaxPartitions(Set<IPartition> partitions){
		List<IPartition> maxPartitions = new ArrayList<>();
		Iterator<IPartition> partIte = partitions.iterator();
		if (partIte.hasNext())
			maxPartitions.add(partIte.next());
		while (partIte.hasNext()) {
			IPartition nextPart = partIte.next();
			boolean isMaximal = true;
			for (int i = 0 ; i < maxPartitions.size() ; i++) {
				Integer comparison = nextPart.compareTo(maxPartitions.get(i));
				if (comparison != null) {
					if (comparison > 0) {
						maxPartitions.remove(i--);
					}
					else {
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

}
