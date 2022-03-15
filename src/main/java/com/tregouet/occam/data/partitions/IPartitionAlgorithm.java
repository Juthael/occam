package com.tregouet.occam.data.partitions;

import java.util.Set;

import com.tregouet.occam.data.representations.IRepresentation;

public interface IPartitionAlgorithm {
	
	Set<IPartition> getIntent();
	
	Set<IRepresentation> getExtent();
	
	double getScore();

}
