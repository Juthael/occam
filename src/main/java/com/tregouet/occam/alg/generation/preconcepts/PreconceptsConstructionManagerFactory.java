package com.tregouet.occam.alg.generation.preconcepts;

import com.tregouet.occam.alg.generation.preconcepts.impl.PreconceptsConstructionManager;

public class PreconceptsConstructionManagerFactory {
	
	public static final PreconceptsConstructionManagerFactory INSTANCE = new PreconceptsConstructionManagerFactory();
	
	private PreconceptsConstructionManagerFactory() {
	}
	
	public PreconceptsConstructionManager apply(PreconceptsConstructionStrategy strategy) {
		switch(strategy) {
			case PRECON_CNSTR_STARTEGY_1 : 
				return new PreconceptsConstructionManager();
			default : 
				return null;
		}
	}

}
