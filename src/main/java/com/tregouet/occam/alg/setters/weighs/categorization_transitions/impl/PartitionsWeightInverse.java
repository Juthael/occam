package com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;

public class PartitionsWeightInverse implements ProblemTransitionWeigher {

	public static final ProblemTransitionWeigher INSTANCE = new PartitionsWeightInverse();

	private PartitionsWeightInverse() {
	}

	@Override
	public void accept(AProblemStateTransition transition) {
		Double weight;
		double sumOfPartitionWeights = 0.0;
		for (IPartition partition : transition.getPartitions())
			sumOfPartitionWeights += partition.weight();
		weight = 1 / sumOfPartitionWeights;
		transition.setWeight(weight);
	}

	@Override
	public PartitionsWeightInverse setContext(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		return this;
	}

}
