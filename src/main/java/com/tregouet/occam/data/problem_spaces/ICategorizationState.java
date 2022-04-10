package com.tregouet.occam.data.problem_spaces;

import java.util.Set;

import com.tregouet.occam.data.logical_structures.orders.partial.PartiallyComparable;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;

public interface ICategorizationState extends PartiallyComparable<ICategorizationState> {
	
	Set<IPartition> getPartitions();
	
	Set<ICategorizationGoalState> getReachableGoalStates();
	
	int id();
	
	void initializeIDGenerator();

}
