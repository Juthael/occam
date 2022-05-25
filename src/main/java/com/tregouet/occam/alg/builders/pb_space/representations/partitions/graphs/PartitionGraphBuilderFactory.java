package com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs;

import com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs.impl.RecursiveForkExploration;

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
