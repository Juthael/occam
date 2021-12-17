package com.tregouet.occam.alg.calculators.costs;

import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public interface ICoster<C extends ICoster<C, D>, D> {
	
	C input(ITransitionFunction transitionFunction);
	
	double evaluateCost(D costed);

}
