package com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;

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
			DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemGraph) {
		return this;
	}

}
