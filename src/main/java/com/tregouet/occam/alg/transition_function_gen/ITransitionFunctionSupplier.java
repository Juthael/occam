package com.tregouet.occam.alg.transition_function_gen;

import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;

public interface ITransitionFunctionSupplier {
	
	IAutomaton getOptimalTransitionFunction();
	
	void reset();

}
