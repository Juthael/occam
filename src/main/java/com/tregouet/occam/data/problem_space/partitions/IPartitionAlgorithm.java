package com.tregouet.occam.data.problem_space.partitions;

import java.util.Set;

import com.tregouet.occam.data.logical_structures.scores.IScore;
import com.tregouet.occam.data.representations.IRepresentation;

public interface IPartitionAlgorithm {
	
	Set<IPartition> getIntent();
	
	<S extends IScore<S>> Set<IRepresentation<S>> getExtent();
	
	double getScore();

}
