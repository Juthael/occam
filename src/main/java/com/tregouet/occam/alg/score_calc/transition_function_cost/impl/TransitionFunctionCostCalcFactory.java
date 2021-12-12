package com.tregouet.occam.alg.score_calc.transition_function_cost.impl;

import java.util.function.Function;

import com.tregouet.occam.alg.score_calc.transition_function_cost.ITransitionFunctionCostCalculator;
import com.tregouet.occam.alg.score_calc.transition_function_cost.TransitionFunctionCostStrategy;
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
