package com.tregouet.occam.alg.transition_function_gen_dep;

import com.tregouet.occam.data.logical_structures.automata.IAutomaton;

public interface ITransitionFunctionSupplier {
	
	IAutomaton getOptimalTransitionFunction();
	
	void reset();

}
