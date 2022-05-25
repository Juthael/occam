package com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;

public class PartitionsWeight implements ProblemTransitionWeigher {

	public static final PartitionsWeight INSTANCE = new PartitionsWeight();

	private PartitionsWeight() {
	}

	@Override
	public void accept(AProblemStateTransition transition) {
		double weight = 0.0;
		for (IPartition partition : transition.getPartitions())
			weight += partition.weight();
		transition.setWeight(weight);
	}

	@Override
	public ProblemTransitionWeigher setContext(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		return this;
	}

}
