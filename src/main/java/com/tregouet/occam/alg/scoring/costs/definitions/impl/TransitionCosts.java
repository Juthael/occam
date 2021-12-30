package com.tregouet.occam.alg.scoring.costs.definitions.impl;

import java.util.List;

import com.tregouet.occam.alg.scoring.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;

public class TransitionCosts implements IDefinitionCoster {

	public static final TransitionCosts INSTANCE = new TransitionCosts();
	private IGenusDifferentiaDefinition definition = null;
	
	private TransitionCosts() {
	}

	@Override
	public IDefinitionCoster input(IGenusDifferentiaDefinition definition) {
		this.definition = definition;
		return this;
	}

	@Override
	public void setCost() {
		double cost = 0.0;
		List<IConjunctiveTransition> transitions = definition.getDifferentiae();
		//HERE
		if (transitions == null)
			System.out.println("here");
		//HERE
		for (IConjunctiveTransition transition : transitions)
			cost += transition.getCostOfComponents();
		definition.setCost(cost);
	}

}
