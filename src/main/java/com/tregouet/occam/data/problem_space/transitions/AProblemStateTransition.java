package com.tregouet.occam.data.problem_space.transitions;

import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.alg.setters.weighs.Weighed;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;

public abstract class AProblemStateTransition extends DefaultEdge implements Weighed {

	private static final long serialVersionUID = 331789410869969821L;

	public abstract Set<IPartition> getPartitions();

	@Override
	public abstract IRepresentation getSource();

	@Override
	public abstract IRepresentation getTarget();

	public abstract void setWeight(Double weight);
	
	public abstract void setRank(int rank);
	
	public abstract int rank();

}
