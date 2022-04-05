package com.tregouet.occam.alg.builders.representations.partitions.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilder;
import com.tregouet.occam.data.problem_space.partitions.IPartition;
import com.tregouet.occam.data.problem_space.partitions.impl.Partition;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.Tree;

public class BuildGraphFirst implements PartitionBuilder {
	
	public static final BuildGraphFirst INSTANCE = new BuildGraphFirst();
	
	private BuildGraphFirst() {
	}

	@Override
	public Set<IPartition> apply(InvertedTree<IConcept, IIsA> treeOfConcepts, IDescription description) {
		Tree<Integer, AbstractDifferentiae> classification = description.asGraph();
		Set<IPartition> partitions = new HashSet<>();
		Set<DirectedAcyclicGraph<Integer, AbstractDifferentiae>> partitionsAsGraph = 
				PartitionBuilder.getPartitionGraphBuilder().apply(classification);
		for (DirectedAcyclicGraph<Integer, AbstractDifferentiae> partitionAsGraph : partitionsAsGraph) {
			String partitionAsString = PartitionBuilder.getPartitionStringBuilder().apply(treeOfConcepts, classification);
			Integer genusID = null;
			Integer[] speciesIDs;
			List<Integer> speciesIDList = new ArrayList<>();
			int maxRank = 0;
			for (AbstractDifferentiae diff : partitionAsGraph.edgeSet()) {
				if (diff.rank() > maxRank) {
					genusID = diff.getSource();
					speciesIDList.clear();
					speciesIDList.add(diff.getTarget());
					maxRank = diff.rank();
				}
				else if (diff.rank() == maxRank)
					if (genusID == null)
						genusID = diff.getSource();
					speciesIDList.add(diff.getTarget());
			}
			speciesIDs = IPartition.orderOverIDs(speciesIDList);
			partitions.add(new Partition(partitionAsGraph, partitionAsString, genusID, speciesIDs));
		}
		return partitions;
	}

}
