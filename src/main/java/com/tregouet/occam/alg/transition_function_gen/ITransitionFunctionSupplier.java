package com.tregouet.occam.alg.transition_function_gen;

import com.tregouet.occam.data.automata.machines.IAutomaton;

public interface ITransitionFunctionSupplier {
	
	IAutomaton getOptimalTransitionFunction();
	
	void reset();

}
