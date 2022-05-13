package com.tregouet.occam.data.problem_spaces.impl;

import java.util.Set;

import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.IRepresentation;

public class ProblemStateTransition extends AProblemStateTransition {

	private static final long serialVersionUID = -8705069997158945728L;

	private final IRepresentation source;
	private final IRepresentation target;
	private final Set<IPartition> partitions;
	private int rank;
	private Double weight = null;

	public ProblemStateTransition(IRepresentation sourceID, IRepresentation targetID, Set<IPartition> partitions) {
		this.source = sourceID;
		this.target = targetID;
		this.partitions = partitions;
	}

	@Override
	public Set<IPartition> getPartitions() {
		return partitions;
	}

	@Override
	public IRepresentation getSource() {
		return source;
	}

	@Override
	public IRepresentation getTarget() {
		return target;
	}

	@Override
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public Double weight() {
		return weight;
	}

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public int rank() {
		return rank;
	}

}
