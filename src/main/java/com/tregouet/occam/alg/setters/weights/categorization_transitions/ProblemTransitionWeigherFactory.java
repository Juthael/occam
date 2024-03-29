package com.tregouet.occam.alg.setters.weights.categorization_transitions;

import com.tregouet.occam.alg.setters.weights.categorization_transitions.impl.PartitionProbWithTrivialTransitionsMandatory;
import com.tregouet.occam.alg.setters.weights.categorization_transitions.impl.PartitionProbability;
import com.tregouet.occam.alg.setters.weights.categorization_transitions.impl.PartitionsWeight;
import com.tregouet.occam.alg.setters.weights.categorization_transitions.impl.PartitionsWeightInverse;

public class ProblemTransitionWeigherFactory {

	public static final ProblemTransitionWeigherFactory INSTANCE = new ProblemTransitionWeigherFactory();

	private ProblemTransitionWeigherFactory() {
	}

	public ProblemTransitionWeigher apply(ProblemTransitionWeigherStrategy strategy) {
		switch (strategy) {
		case PARTITIONS_WEIGHT:
			return PartitionsWeight.INSTANCE;
		case PARTITIONS_WEIGHT_INVERSE:
			return PartitionsWeightInverse.INSTANCE;
		case PARTITION_PROBABILITY :
			return new PartitionProbability();
		case PART_PROB_WITH_TRIVIAL_TRANSITIONS_MANDATORY :
			return new PartitionProbWithTrivialTransitionsMandatory();
		default:
			return null;
		}
	}

}
