package com.tregouet.occam.alg.calculators.costs.dep.transition_function.impl;

import java.util.function.Function;

import com.tregouet.occam.alg.calculators.costs.dep.transition_function.ITransitionFunctionCostCalculator;
import com.tregouet.occam.alg.calculators.costs.dep.transition_function.TransitionFunctionCostStrategy;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public class TransitionFunctionCostCalcFactory 
	implements Function<TransitionFunctionCostStrategy, Function<ITransitionFunction, Double>> {

	public static final TransitionFunctionCostCalcFactory INSTANCE = new TransitionFunctionCostCalcFactory();
	
	private TransitionFunctionCostCalcFactory() {
	}
	
	@Override
	public ITransitionFunctionCostCalculator apply(TransitionFunctionCostStrategy tfCostStrategy) {
		switch(tfCostStrategy) {
			case NUMBER_OF_TRANSITIONS :
				return NumberOfTransitions.INSTANCE;
			default : 
				return null;
		}
	}

}
