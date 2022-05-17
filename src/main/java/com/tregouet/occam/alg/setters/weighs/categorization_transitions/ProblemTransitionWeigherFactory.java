package com.tregouet.occam.alg.setters.weighs.categorization_transitions;

import com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl.PartitionProbability;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl.PartitionsWeight;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl.PartitionsWeightInverse;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl.RankedPartitionsWeight;

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
		case RANKED_PARTITIONS_WEIGHT : 
			return RankedPartitionsWeight.INSTANCE;
		case PARTITION_PROBABILITY : 
			return new PartitionProbability();
		default:
			return null;
		}
	}

}
