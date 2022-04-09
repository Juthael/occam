package com.tregouet.occam.data.problem_spaces.impl;

import java.util.Set;

import com.tregouet.occam.data.problem_spaces.ACategorizationStateTransition;
import com.tregouet.occam.data.representations.partitions.IPartition;

public class CategorizationTransition extends ACategorizationStateTransition {
	
	private static final long serialVersionUID = -8705069997158945728L;

	private final Integer sourceID;
	private final Integer targetID;
	private final Set<IPartition> partitions;
	private Double weight = null;
	
	public CategorizationTransition(Integer sourceID, Integer targetID, Set<IPartition> partitions) {
		this.sourceID = sourceID;
		this.targetID = targetID;
		this.partitions = partitions;
	}
	
	@Override
	public Double weight() {
		return weight;
	}

	@Override
	public Set<IPartition> getPartitions() {
		return partitions;
	}

	@Override
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	@Override
	public Integer getSource() {
		return sourceID;
	}
	
	@Override
	public Integer getTarget() {
		return targetID;
	}

}
