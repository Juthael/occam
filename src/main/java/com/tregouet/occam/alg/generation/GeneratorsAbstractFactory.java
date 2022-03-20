package com.tregouet.occam.alg.generation;

import com.tregouet.occam.alg.generation.preconcepts.IPreconceptsConstructionManager;
import com.tregouet.occam.alg.generation.preconcepts.PreconceptsConstructionManagerFactory;
import com.tregouet.occam.alg.generation.preconcepts.PreconceptsConstructionStrategy;
import com.tregouet.occam.alg.generation.representation.transitions_gen.ITransitionsConstructionManager;
import com.tregouet.occam.alg.generation.representation.transitions_gen.TransitionsConstructionManagerFactory;
import com.tregouet.occam.alg.generation.representation.transitions_gen.TransitionsConstructionStrategy;

public class GeneratorsAbstractFactory {
	
	public static final GeneratorsAbstractFactory INSTANCE = new GeneratorsAbstractFactory();
	
	private PreconceptsConstructionStrategy preconceptsConstructionStrategy = null;
	private TransitionsConstructionStrategy transitionsConstructionStrategy = null;
	
	private GeneratorsAbstractFactory() {
	}
	
	public void setUpStrategy(GenerationStrategy overallStrategy) {
		switch(overallStrategy) {
			case GENERATION_STRATEGY_1 : 
				preconceptsConstructionStrategy = PreconceptsConstructionStrategy.PRECON_CNSTR_STARTEGY_1;
				transitionsConstructionStrategy = TransitionsConstructionStrategy.TRANSITIONS_CNSTR_STRATEGY_1;
				break;
			default : 
				break;
		}
	}
	
	public IPreconceptsConstructionManager getPreconceptsConstructionManager() {
		return PreconceptsConstructionManagerFactory.INSTANCE.apply(preconceptsConstructionStrategy);
	}
	
	public ITransitionsConstructionManager getTransitionsConstructionManager() {
		return TransitionsConstructionManagerFactory.INSTANCE.apply(transitionsConstructionStrategy);
	}

}
