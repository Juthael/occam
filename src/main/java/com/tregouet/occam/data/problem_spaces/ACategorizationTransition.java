package com.tregouet.occam.data.problem_spaces;

import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.alg.setters.weighs.Weighed;
import com.tregouet.occam.data.representations.partitions.IPartition;

public abstract class ACategorizationTransition extends DefaultEdge implements Weighed {
	
	private static final long serialVersionUID = 331789410869969821L;

	public abstract Set<IPartition> getPartitions();
	
	public abstract void setWeight(Double weight);
	
	@Override
	public abstract ICategorizationState getSource();
	
	@Override
	public abstract ICategorizationState getTarget();

}
