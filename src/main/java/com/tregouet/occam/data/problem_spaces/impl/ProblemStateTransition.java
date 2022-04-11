package com.tregouet.occam.data.problem_spaces.impl;

import java.util.Set;

import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;

public class ProblemStateTransition extends AProblemStateTransition {
	
	private static final long serialVersionUID = -8705069997158945728L;

	private final IProblemState source;
	private final IProblemState target;
	private final Set<IPartition> partitions;
	private Double weight = null;
	
	public ProblemStateTransition(IProblemState sourceID, IProblemState targetID, Set<IPartition> partitions) {
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
	public IProblemState getSource() {
		return source;
	}
	
	@Override
	public IProblemState getTarget() {
		return target;
	}

}
