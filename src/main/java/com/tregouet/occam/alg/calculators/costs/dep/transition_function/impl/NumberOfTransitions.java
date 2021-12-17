package com.tregouet.occam.alg.calculators.costs.dep.transition_function.impl;

import com.tregouet.occam.alg.calculators.costs.dep.transition_function.ITransitionFunctionCostCalculator;
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
