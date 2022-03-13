package com.tregouet.occam.alg.scoring_dep.costs.definitions.impl;

import java.util.List;

import com.tregouet.occam.alg.scoring_dep.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.data.automata.machines.deprec.IGenusDifferentiaDefinition_dep;
import com.tregouet.occam.data.automata.transitions.IConjunctiveTransition;

public class TransitionCosts implements IDefinitionCoster {

	public static final TransitionCosts INSTANCE = new TransitionCosts();
	private IGenusDifferentiaDefinition_dep definition = null;
	
	private TransitionCosts() {
	}

	@Override
	public IDefinitionCoster input(IGenusDifferentiaDefinition_dep definition) {
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
