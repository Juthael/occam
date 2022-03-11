package com.tregouet.occam.alg.scoring_dep.costs.transitions.impl;

import com.google.common.base.Function;
import com.tregouet.occam.alg.scoring_dep.costs.transitions.ITransitionCoster;
import com.tregouet.occam.alg.scoring_dep.costs.transitions.TransitionCostingStrategy;

public class TransitionCosterFactory implements
		Function<TransitionCostingStrategy, ITransitionCoster> {

	public static final TransitionCosterFactory INSTANCE = new TransitionCosterFactory();
	
	public TransitionCosterFactory() {
	}

	@Override
	public ITransitionCoster apply(TransitionCostingStrategy strategy) {
		switch (strategy) {
			case ENTROPY_REDUCTION : 
				return EntropyReduction.INSTANCE;
			case IF_PROPERTY_THEN_ONE : 
				return IfPropertyThenOne.INSTANCE;
			case COSTLESS : 
				return Costless.INSTANCE;
			default : 
				return null;
		}
	}

}
