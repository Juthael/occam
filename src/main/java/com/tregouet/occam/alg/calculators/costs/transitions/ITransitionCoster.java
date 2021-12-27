package com.tregouet.occam.alg.calculators.costs.transitions;

import com.tregouet.occam.alg.calculators.costs.ICoster;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;

public interface ITransitionCoster extends ICoster<ITransitionCoster, ITransition> {
	
	void setNewTransitionFunctionParameter(ITransitionFunction transitionFunction);

}
