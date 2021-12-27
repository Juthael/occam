package com.tregouet.occam.alg.calculators.costs.definitions.impl;

import com.google.common.base.Function;
import com.tregouet.occam.alg.calculators.costs.definitions.DefinitionCostingStrategy;
import com.tregouet.occam.alg.calculators.costs.definitions.IDefinitionCoster;

public class DefinitionCosterFactory implements Function<DefinitionCostingStrategy, IDefinitionCoster> {

	public static final DefinitionCosterFactory INSTANCE = new DefinitionCosterFactory();
	
	private DefinitionCosterFactory() {
	}

	@Override
	public IDefinitionCoster apply(DefinitionCostingStrategy strategy) {
		switch (strategy) {
			case TRANSITION_COSTS : 
				return DifferentiaeCosts.INSTANCE;
			default : 
				return null;
		}
	}

}
