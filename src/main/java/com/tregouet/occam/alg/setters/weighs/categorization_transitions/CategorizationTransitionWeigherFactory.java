package com.tregouet.occam.alg.setters.weighs.categorization_transitions;

import com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl.InverseOfPartitionsWeight;

public class CategorizationTransitionWeigherFactory {
	
	public static final CategorizationTransitionWeigherFactory INSTANCE = 
			new CategorizationTransitionWeigherFactory();
	
	private CategorizationTransitionWeigherFactory() {
	}
	
	public CategorizationTransitionWeigher apply(CategorizationTransitionWeigherStrategy strategy) {
		switch(strategy) {
			case INVERSE_OF_PARTITIONS_WEIGHT : 
				return InverseOfPartitionsWeight.INSTANCE;
			default : 
				return null;
		}
	}

}
