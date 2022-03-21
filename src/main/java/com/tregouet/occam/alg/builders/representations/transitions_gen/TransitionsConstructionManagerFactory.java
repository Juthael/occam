package com.tregouet.occam.alg.builders.representations.transitions_gen;

import com.tregouet.occam.alg.builders.representations.transitions_gen.impl.TransitionsConstructionManager;

public class TransitionsConstructionManagerFactory {
	
	public static final TransitionsConstructionManagerFactory INSTANCE = new TransitionsConstructionManagerFactory();
	
	private TransitionsConstructionManagerFactory() {
	}
	
	public ITransitionsConstructionManager apply(TransitionsConstructionStrategy strategy) {
		switch(strategy) {
			case TRANSITIONS_CNSTR_STRATEGY_1 :
				return new TransitionsConstructionManager();
			default :
				return null;
		}
	}

}
