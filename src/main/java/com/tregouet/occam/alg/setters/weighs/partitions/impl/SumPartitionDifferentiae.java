package com.tregouet.occam.alg.setters.weighs.partitions.impl;

import com.tregouet.occam.alg.setters.weighs.partitions.PartitionWeigher;
import com.tregouet.occam.data.problem_space.partitions.IPartition;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;

public class SumPartitionDifferentiae implements PartitionWeigher {
	
	public static final SumPartitionDifferentiae INSTANCE = new SumPartitionDifferentiae();
	
	private SumPartitionDifferentiae() {
	}

	@Override
	public void accept(IPartition partition) {
		double weight = 0.0;
		for (AbstractDifferentiae differentiae : partition.getDifferentiae())
			weight += differentiae.weight();
		partition.setWeight(weight);
	}

}
