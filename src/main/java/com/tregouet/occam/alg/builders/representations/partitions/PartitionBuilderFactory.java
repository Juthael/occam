package com.tregouet.occam.alg.builders.representations.partitions;

import com.tregouet.occam.alg.builders.representations.partitions.impl.BuildGraphFirst;

public class PartitionBuilderFactory {

	public static final PartitionBuilderFactory INSTANCE = new PartitionBuilderFactory();

	private PartitionBuilderFactory() {
	}

	public PartitionBuilder apply(PartitionBuilderStrategy strategy) {
		switch (strategy) {
		case BUILD_GRAPH_FIRST:
			return new BuildGraphFirst();
		default:
			return null;
		}
	}

}
