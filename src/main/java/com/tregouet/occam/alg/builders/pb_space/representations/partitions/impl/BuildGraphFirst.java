package com.tregouet.occam.alg.builders.pb_space.representations.partitions.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.displayers.formatters.sortings.Sorting2StringConverter;
import com.tregouet.occam.data.modules.categorization.transitions.partitions.IPartition;
import com.tregouet.occam.data.modules.categorization.transitions.partitions.impl.Partition;
import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class BuildGraphFirst implements PartitionBuilder {

	private Map<Integer, List<Integer>> conceptID2ExtentIDs = new HashMap<>();

	public BuildGraphFirst() {
	}

	@Override
	public Set<IPartition> apply(IDescription description, IClassification classification) {
		conceptID2ExtentIDs = classification.mapConceptID2ExtentIDs();
		Tree<Integer, ADifferentiae> descGraph = description.asGraph();
		Set<IPartition> partitions = new HashSet<>();
		Set<Tree<Integer, ADifferentiae>> partitionsAsGraph = PartitionBuilder.getPartitionGraphBuilder()
				.apply(descGraph);
		Sorting2StringConverter stringBuilder = PartitionBuilder.getSorting2StringConverter().setUp(conceptID2ExtentIDs);
		for (Tree<Integer, ADifferentiae> partitionAsGraph : partitionsAsGraph) {
			// set partitionAsString
			String partitionAsString = stringBuilder.apply(partitionAsGraph);
			// set genusID and edgeIDs
			Integer genusID = null;
			Integer[] speciesIDs;
			List<Integer> speciesIDList = new ArrayList<>();
			int maxRank = 0;
			for (ADifferentiae diff : partitionAsGraph.edgeSet()) {
				if (diff.rank() > maxRank) {
					genusID = diff.getSource();
					speciesIDList.clear();
					speciesIDList.add(diff.getTarget());
					maxRank = diff.rank();
				} else if (diff.rank() == maxRank) {
					if (genusID == null)
						genusID = diff.getSource();
					speciesIDList.add(diff.getTarget());
				}
			}
			// set leaf2Extent and rank
			Map<Integer, List<Integer>> leaf2Extent = new HashMap<>();
			for (Integer leafID : partitionAsGraph.getLeaves())
				leaf2Extent.put(leafID, conceptID2ExtentIDs.get(leafID));
			speciesIDs = IPartition.orderOverIDs(speciesIDList);
			// instantiate
			IPartition partition = new Partition(partitionAsGraph, partitionAsString, genusID, speciesIDs, leaf2Extent);
			PartitionBuilder.getPartitionWeigher().accept(partition);
			partitions.add(partition);
		}
		return partitions;
	}

}
