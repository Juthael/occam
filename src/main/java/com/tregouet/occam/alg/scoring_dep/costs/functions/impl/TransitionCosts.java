package com.tregouet.occam.alg.scoring_dep.costs.functions.impl;

import com.tregouet.occam.alg.scoring_dep.costs.functions.IFunctionCoster;
import com.tregouet.occam.data.automata.machines.IAutomaton;
import com.tregouet.occam.data.automata.transitions.IConjunctiveTransition;

public class TransitionCosts implements IFunctionCoster {

	public static final TransitionCosts INSTANCE = new TransitionCosts();
	private IAutomaton automaton = null;
	
	private TransitionCosts() {
	}

	@Override
	public IFunctionCoster input(IAutomaton automaton) {
		this.automaton = automaton;
		return this;
	}

	@Override
	public void setCost() {
		double cost = 0.0;
		for (IConjunctiveTransition transition : automaton.getConjunctiveTransitions()) {
			cost += transition.getCostOfComponents();
		}
		automaton.setCost(cost);
	}

}
