package com.tregouet.occam.alg.score_calc.transition_function_cost.impl;

import com.tregouet.occam.alg.score_calc.transition_function_cost.ITransitionFunctionCostCalculator;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public class NumberOfTransitions implements ITransitionFunctionCostCalculator {
	
	public static final NumberOfTransitions INSTANCE = new NumberOfTransitions();
	
	private NumberOfTransitions() {
	}
	
	
	@Override
	public Double apply(ITransitionFunction transitionFunction) {
		return (double) transitionFunction.getTransitions().size();
	} 
}
