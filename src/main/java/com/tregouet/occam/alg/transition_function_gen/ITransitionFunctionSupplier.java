package com.tregouet.occam.alg.transition_function_gen;

import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public interface ITransitionFunctionSupplier {
	
	ITransitionFunction getOptimalTransitionFunction();
	
	void reset();

}
