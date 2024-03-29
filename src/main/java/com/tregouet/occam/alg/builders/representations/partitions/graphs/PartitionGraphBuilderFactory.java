package com.tregouet.occam.alg.builders.representations.partitions.graphs;

import com.tregouet.occam.alg.builders.representations.partitions.graphs.impl.RecursiveForkExploration;

public class PartitionGraphBuilderFactory {

	public static final PartitionGraphBuilderFactory INSTANCE = new PartitionGraphBuilderFactory();

	private PartitionGraphBuilderFactory() {
	}

	public PartitionGraphBuilder apply(PartitionGraphBuilderStrategy strategy) {
		switch (strategy) {
		case RECURSIVE_FORK_EXPLORATION:
			return RecursiveForkExploration.INSTANCE;
		default:
			return null;
		}
	}

}
