package com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.weighs.categorization_transitions.CategorizationTransitionWeigher;
import com.tregouet.occam.data.problem_spaces.ACategorizationTransition;
import com.tregouet.occam.data.problem_spaces.ICategorizationState;
import com.tregouet.occam.data.representations.partitions.IPartition;

public class InverseOfPartitionsWeight implements CategorizationTransitionWeigher {

	public static final CategorizationTransitionWeigher INSTANCE = new InverseOfPartitionsWeight();
	
	private InverseOfPartitionsWeight() {
	}
	
	@Override
	public void accept(ACategorizationTransition transition) {
		Double weight;
		double sumOfPartitionWeights = 0.0;
		for (IPartition partition : transition.getPartitions())
			sumOfPartitionWeights += partition.weight();
		weight = 1/sumOfPartitionWeights;
		transition.setWeight(weight);
	}

	@Override
	public InverseOfPartitionsWeight setContext(
			DirectedAcyclicGraph<ICategorizationState, ACategorizationTransition> problemGraph) {
		return this;
	}

}
