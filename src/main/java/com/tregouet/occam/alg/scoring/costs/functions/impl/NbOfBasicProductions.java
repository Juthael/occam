package com.tregouet.occam.alg.scoring.costs.functions.impl;

import com.tregouet.occam.alg.scoring.costs.functions.IFunctionCoster;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.transition_rules.IConjunctiveTransition;

public class NbOfBasicProductions implements IFunctionCoster {
	
	public static final NbOfBasicProductions INSTANCE = new NbOfBasicProductions();
	private ITransitionFunction transitionFunction = null;
	
	private NbOfBasicProductions() {
	}

	@Override
	public IFunctionCoster input(ITransitionFunction transitionFunction) {
		this.transitionFunction = transitionFunction;
		return this;
	}

	@Override
	public void setCost() {
		int nbOfBasicProductions = 0;
		for (IConjunctiveTransition conjTransition : transitionFunction.getConjunctiveTransitions()) {
			nbOfBasicProductions += conjTransition.howManyBasicProductions();
		}
		transitionFunction.setCost((double) nbOfBasicProductions);
	}

}
