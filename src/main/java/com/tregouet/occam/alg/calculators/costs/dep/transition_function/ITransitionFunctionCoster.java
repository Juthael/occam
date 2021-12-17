package com.tregouet.occam.alg.calculators.costs.dep.transition_function;

import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public interface ITransitionFunctionCoster {
	
	double evaluateCost(ITransitionFunction transitionFunction);

}
