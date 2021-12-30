package com.tregouet.occam.alg.scoring.costs.definitions.impl;

import com.google.common.base.Function;
import com.tregouet.occam.alg.scoring.costs.definitions.DefinitionCostingStrategy;
import com.tregouet.occam.alg.scoring.costs.definitions.IDefinitionCoster;

public class DefinitionCosterFactory implements Function<DefinitionCostingStrategy, IDefinitionCoster> {

	public static final DefinitionCosterFactory INSTANCE = new DefinitionCosterFactory();
	
	private DefinitionCosterFactory() {
	}

	@Override
	public IDefinitionCoster apply(DefinitionCostingStrategy strategy) {
		switch (strategy) {
			case TRANSITION_COSTS : 
				return TransitionCosts.INSTANCE;
			case TRANSITIONS_ENTROPY_REDUCTION : 
				return TransitionsEntropyReduction.INSTANCE;
			case PRODUCTIONS_ENTROPY_REDUCTION : 
				return ProductionsEntropyReduction.INSTANCE;
			case INSTANTIATIONS_ENTROPY_REDUCTION : 
				return InstantiationsEntropyReduction.INSTANCE;
			default : 
				return null;
		}
	}

}
