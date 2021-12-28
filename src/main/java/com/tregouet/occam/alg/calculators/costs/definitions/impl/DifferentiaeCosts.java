package com.tregouet.occam.alg.calculators.costs.definitions.impl;

import java.util.List;

import com.tregouet.occam.alg.calculators.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;

public class DifferentiaeCosts implements IDefinitionCoster {

	public static final DifferentiaeCosts INSTANCE = new DifferentiaeCosts();
	private IGenusDifferentiaDefinition definition = null;
	
	private DifferentiaeCosts() {
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
		for (IConjunctiveTransition transition : transitions)
			cost += transition.getCostOfComponents();
		definition.setCost(cost);
	}

}
