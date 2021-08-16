package com.tregouet.occam.transition_function;

public interface ITransitionFunctionSupplier {
	
	ITransitionFunction getOptimalTransitionFunction();
	
	void reset();

}
