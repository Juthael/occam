package com.tregouet.occam.alg.calculators.costs.transitions.impl;

import com.google.common.base.Function;
import com.tregouet.occam.alg.calculators.costs.transitions.ITransitionCoster;
import com.tregouet.occam.alg.calculators.costs.transitions.TransitionCostingStrategy;

public class TransitionCosterFactory implements
		Function<TransitionCostingStrategy, ITransitionCoster> {

	public static final TransitionCosterFactory INSTANCE = new TransitionCosterFactory();
	
	public TransitionCosterFactory() {
	}

	@Override
	public ITransitionCoster apply(TransitionCostingStrategy strategy) {
		switch (strategy) {
			case ENTROPY_REDUCTION : 
				return new EntropyReduction();
			default : 
				return null;
		}
	}

}
