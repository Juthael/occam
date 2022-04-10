package com.tregouet.occam.data.problem_spaces.impl;

import java.util.Set;

import com.tregouet.occam.data.problem_spaces.ACategorizationTransition;
import com.tregouet.occam.data.problem_spaces.ICategorizationState;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;

public class CategorizationTransition extends ACategorizationTransition {
	
	private static final long serialVersionUID = -8705069997158945728L;

	private final ICategorizationState source;
	private final ICategorizationState target;
	private final Set<IPartition> partitions;
	private Double weight = null;
	
	public CategorizationTransition(ICategorizationState sourceID, ICategorizationState targetID, Set<IPartition> partitions) {
		this.source = sourceID;
		this.target = targetID;
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
	public ICategorizationState getSource() {
		return source;
	}
	
	@Override
	public ICategorizationState getTarget() {
		return target;
	}

}
