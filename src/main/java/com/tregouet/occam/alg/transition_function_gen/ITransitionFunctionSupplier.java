package com.tregouet.occam.alg.transition_function_gen;

import com.tregouet.occam.data.automata.IAutomaton;

public interface ITransitionFunctionSupplier {
	
	IAutomaton getOptimalTransitionFunction();
	
	void reset();

}
