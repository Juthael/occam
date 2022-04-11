package com.tregouet.occam.alg.setters.weighs.categorization_transitions;

import com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl.PartitionsWeight;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl.PartitionsWeightInverse;

public class CategorizationTransitionWeigherFactory {
	
	public static final CategorizationTransitionWeigherFactory INSTANCE = 
			new CategorizationTransitionWeigherFactory();
	
	private CategorizationTransitionWeigherFactory() {
	}
	
	public CategorizationTransitionWeigher apply(CategorizationTransitionWeigherStrategy strategy) {
		switch(strategy) {
			case PARTITIONS_WEIGHT : 
				return PartitionsWeight.INSTANCE;
			case PARTITIONS_WEIGHT_INVERSE : 
				return PartitionsWeightInverse.INSTANCE;
			default : 
				return null;
		}
	}

}
