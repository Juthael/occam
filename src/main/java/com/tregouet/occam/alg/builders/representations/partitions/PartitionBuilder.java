package com.tregouet.occam.alg.builders.representations.partitions;

import java.util.Set;

import com.google.common.base.Function;
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.partitions.as_graphs.PartitionGraphBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.as_strings.PartitionStringBuilder;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.weighs.partitions.PartitionWeigher;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.tree_finder.data.InvertedTree;

public interface PartitionBuilder extends Function<IDescription, Set<IPartition>> {
	
	public PartitionBuilder setUp(InvertedTree<IConcept, IIsA> treeOfConcepts);
	
	public static PartitionGraphBuilder getPartitionGraphBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getPartitionGraphBuilder();
	}
	
	public static PartitionStringBuilder getPartitionStringBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getPartitionStringBuilder();
	}
	
	public static PartitionWeigher getPartitionWeigher() {
		return SettersAbstractFactory.INSTANCE.getPartitionWeigher();
	}

}
