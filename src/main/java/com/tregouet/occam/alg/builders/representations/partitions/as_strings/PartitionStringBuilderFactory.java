package com.tregouet.occam.alg.builders.representations.partitions.as_strings;

import com.tregouet.occam.alg.builders.representations.partitions.as_strings.impl.RecursiveFraming;

public class PartitionStringBuilderFactory {
	
	public static final PartitionStringBuilderFactory INSTANCE = new PartitionStringBuilderFactory();
	
	private PartitionStringBuilderFactory() {
	}
	
	public PartitionStringBuilder apply(PartitionStringBuilderStrategy strategy) {
		switch(strategy) {
			case RECURSIVE_FRAMING : 
				return new RecursiveFraming();
			default : 
				return null;
		}
	}

}
