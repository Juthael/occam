package com.tregouet.occam.transition_function;

import java.util.Iterator;

public interface ITransitionFunctionSupplier extends Iterator<ITransitionFunction> {
	
	ITransitionFunction getOptimalTransitionFunction();
	
	void reset();

}
