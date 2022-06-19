package com.tregouet.occam.alg.builders.pb_space.representations.partitions.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

//of no use for now
public class PartitionRanker {

	public static PartitionRanker INSTANCE = new PartitionRanker();

	private PartitionRanker() {
	}

	public int rank(Tree<Integer, ADifferentiae> partitionGraph) {
		return rank(partitionGraph, partitionGraph.getRoot(), 0);
	}

	private int rank(Tree<Integer, ADifferentiae> partitionGraph, Integer vertex, int rank) {
		Set<ADifferentiae> vertexOutgoingEdges = partitionGraph.outgoingEdgesOf(vertex);
		if (vertexOutgoingEdges.isEmpty())
			return rank;
		else {
			List<Integer> successors = new ArrayList<>(vertexOutgoingEdges.size());
			for (ADifferentiae edge : vertexOutgoingEdges)
				successors.add(partitionGraph.getEdgeTarget(edge));
			int maxRank = 0;
			for (Integer successor : successors) {
				int thisBranchMaxRank = rank(partitionGraph, successor, rank+1);
				if (maxRank < thisBranchMaxRank)
					maxRank = thisBranchMaxRank;
			}
			return maxRank;
		}
	}

}
