package com.tregouet.occam.alg.builders;

import com.tregouet.occam.alg.builders.preconcepts.IPreconceptsConstructionManager;
import com.tregouet.occam.alg.builders.preconcepts.PreconceptsConstructionManagerFactory;
import com.tregouet.occam.alg.builders.preconcepts.PreconceptsConstructionStrategy;
import com.tregouet.occam.alg.builders.representations.properties.IPropertyBuilder;
import com.tregouet.occam.alg.builders.representations.properties.PropertyBuilderFactory;
import com.tregouet.occam.alg.builders.representations.properties.PropertyConstructionStrategy;
import com.tregouet.occam.alg.builders.representations.transitions_gen.ITransitionsConstructionManager;
import com.tregouet.occam.alg.builders.representations.transitions_gen.TransitionsConstructionManagerFactory;
import com.tregouet.occam.alg.builders.representations.transitions_gen.TransitionsConstructionStrategy;

public class GeneratorsAbstractFactory {
	
	public static final GeneratorsAbstractFactory INSTANCE = new GeneratorsAbstractFactory();
	
	private PreconceptsConstructionStrategy preconceptsConstructionStrategy = null;
	private TransitionsConstructionStrategy transitionsConstructionStrategy = null;
	private PropertyConstructionStrategy propertyConstructionStrategy = null;
	
	private GeneratorsAbstractFactory() {
	}
	
	public void setUpStrategy(GenerationStrategy overallStrategy) {
		switch(overallStrategy) {
			case GENERATION_STRATEGY_1 : 
				preconceptsConstructionStrategy = PreconceptsConstructionStrategy.PRECON_CNSTR_STARTEGY_1;
				transitionsConstructionStrategy = TransitionsConstructionStrategy.TRANSITIONS_CNSTR_STRATEGY_1;
				propertyConstructionStrategy = PropertyConstructionStrategy.GROUP_APPLICATIONS_BY_FUNCTION;
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
	
	public IPropertyBuilder getPropertyBuilder() {
		return PropertyBuilderFactory.INSTANCE.apply(propertyConstructionStrategy);
	}

}
