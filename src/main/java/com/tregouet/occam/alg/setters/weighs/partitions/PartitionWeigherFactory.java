package com.tregouet.occam.alg.setters.weighs.partitions;

import com.tregouet.occam.alg.setters.weighs.partitions.impl.SumPartitionDifferentiae;

public class PartitionWeigherFactory {

	public static final PartitionWeigherFactory INSTANCE = new PartitionWeigherFactory();

	private PartitionWeigherFactory() {
	}

	public PartitionWeigher apply(PartitionWeigherStrategy strategy) {
		switch(strategy) {
			case SUM_PARTITION_DIFFERENTIAE :
				return SumPartitionDifferentiae.INSTANCE;
			default :
				return null;
		}
	}

}
