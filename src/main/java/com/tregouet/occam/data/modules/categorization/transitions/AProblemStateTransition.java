package com.tregouet.occam.data.modules.categorization.transitions;

import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.alg.setters.weights.Weighed;
import com.tregouet.occam.data.modules.categorization.transitions.partitions.IPartition;
import com.tregouet.occam.data.structures.representations.IRepresentation;

public abstract class AProblemStateTransition extends DefaultEdge implements Weighed {

	private static final long serialVersionUID = 331789410869969821L;

	public abstract Set<IPartition> getPartitions();

	@Override
	public abstract IRepresentation getSource();

	@Override
	public abstract IRepresentation getTarget();

	public abstract void setWeight(Double weight);

}
