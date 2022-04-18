package com.tregouet.occam.data.problem_spaces;

import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.alg.setters.weighs.Weighed;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;

public abstract class AProblemStateTransition extends DefaultEdge implements Weighed {

	private static final long serialVersionUID = 331789410869969821L;

	public abstract Set<IPartition> getPartitions();

	@Override
	public abstract IProblemState getSource();

	@Override
	public abstract IProblemState getTarget();

	public abstract void setWeight(Double weight);

}
