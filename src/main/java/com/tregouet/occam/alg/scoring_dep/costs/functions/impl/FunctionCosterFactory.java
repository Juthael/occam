package com.tregouet.occam.alg.scoring_dep.costs.functions.impl;

import com.google.common.base.Function;
import com.tregouet.occam.alg.scoring_dep.costs.functions.FunctionCostingStrategy;
import com.tregouet.occam.alg.scoring_dep.costs.functions.IFunctionCoster;

public class FunctionCosterFactory implements
		Function<FunctionCostingStrategy, IFunctionCoster> {

	public static final FunctionCosterFactory INSTANCE = new FunctionCosterFactory();
	
	private FunctionCosterFactory() {
	}

	@Override
	public IFunctionCoster apply(FunctionCostingStrategy strategy) {
		switch (strategy) {
			case TRANSITION_COSTS : 
				return TransitionCosts.INSTANCE;
			case NB_OF_INSTANTIATED_VARIABLES : 
				return NbOfInstantiatedVariables.INSTANCE;
			case NB_OF_BASIC_PRODUCTIONS : 
				return NbOfBasicProductions.INSTANCE;
			default : 
				return null;
		}
	}

}
