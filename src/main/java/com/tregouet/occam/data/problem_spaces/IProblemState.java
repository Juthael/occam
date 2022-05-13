package com.tregouet.occam.data.problem_spaces;

import java.util.Set;

import com.tregouet.occam.alg.scorers.Scored;
import com.tregouet.occam.data.logical_structures.orders.partial.PartiallyComparable;
import com.tregouet.occam.data.logical_structures.orders.total.impl.LecticScore;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;

public interface IProblemState extends PartiallyComparable<IProblemState>, Scored<LecticScore> {

	@Override
	boolean equals(Object o);

	Set<IPartition> getPartitions();

	@Override
	int hashCode();

	int id();

	void initializeIDGenerator();
	
	boolean isGoalState();

}
