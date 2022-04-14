package com.tregouet.occam.alg.builders.representations.partitions.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.representations.string_scheme.StringSchemeBuilder;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.problem_spaces.partitions.impl.Partition;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class BuildGraphFirst implements PartitionBuilder {
	
	Map<Integer, List<Integer>> conceptID2ExtentIDs = new HashMap<Integer, List<Integer>>(); 
	
	public BuildGraphFirst() {
	}

	@Override
	public Set<IPartition> apply(IDescription description) {
		Tree<Integer, AbstractDifferentiae> classification = description.asGraph();
		Set<IPartition> partitions = new HashSet<>();
		Set<Tree<Integer, AbstractDifferentiae>> partitionsAsGraph = 
				PartitionBuilder.getPartitionGraphBuilder().apply(classification);
		StringSchemeBuilder stringBuilder = PartitionBuilder.getPartitionStringBuilder().setUp(conceptID2ExtentIDs);
		for (Tree<Integer, AbstractDifferentiae> partitionAsGraph : partitionsAsGraph) {
			//set partitionAsString
			String partitionAsString = stringBuilder.apply(partitionAsGraph);
			//set genusID and edgeIDs
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
			//set leaf2Extent
			Map<Integer, List<Integer>> leaf2Extent = new HashMap<>();
			for (Integer leafID : partitionAsGraph.getLeaves())
				leaf2Extent.put(leafID, conceptID2ExtentIDs.get(leafID));
			speciesIDs = IPartition.orderOverIDs(speciesIDList);
			//instantiate
			IPartition partition = new Partition(partitionAsGraph, partitionAsString, genusID, speciesIDs, leaf2Extent);
			PartitionBuilder.getPartitionWeigher().accept(partition);
			partitions.add(partition);
		}
		return partitions;
	}

	@Override
	public PartitionBuilder setUp(InvertedTree<IConcept, IIsA> treeOfConcepts) {
		for(IConcept concept : treeOfConcepts) {
			if (concept.type() == ConceptType.PARTICULAR)
				conceptID2ExtentIDs.put(concept.iD(), new ArrayList<>(Arrays.asList(new Integer[] {concept.iD()})));
			else {
				List<Integer> extentIDs = new ArrayList<>();
				Set<IConcept> extent = Sets.intersection(
						Functions.lowerSet(treeOfConcepts, concept), 
						treeOfConcepts.getLeaves());
				for (IConcept particular : extent)
					extentIDs.add(particular.iD());
				conceptID2ExtentIDs.put(concept.iD(), extentIDs);
			}
		}
		return this;
	}
	
	

}
