package com.tregouet.occam.data.representations.partitions;

import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;

public abstract class APartitionOperator extends DefaultWeightedEdge {

	private static final long serialVersionUID = 5657917228607952138L;
	
	abstract Set<IPartition> getDistinctivePartitions();

}
