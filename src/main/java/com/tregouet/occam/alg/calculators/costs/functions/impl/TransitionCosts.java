package com.tregouet.occam.alg.calculators.costs.functions.impl;

import com.tregouet.occam.alg.calculators.costs.functions.IFunctionCoster;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;

public class TransitionCosts implements IFunctionCoster {

	public static final TransitionCosts INSTANCE = new TransitionCosts();
	private ITransitionFunction transitionFunction = null;
	
	private TransitionCosts() {
	}

	@Override
	public IFunctionCoster input(ITransitionFunction transitionFunction) {
		this.transitionFunction = transitionFunction;
		return this;
	}

	@Override
	public void setCost() {
		double cost = 0.0;
		for (IConjunctiveTransition transition : transitionFunction.getConjunctiveTransitions()) {
			cost += transition.getCostOfComponents();
		}
		transitionFunction.setCost(cost);
	}

}
