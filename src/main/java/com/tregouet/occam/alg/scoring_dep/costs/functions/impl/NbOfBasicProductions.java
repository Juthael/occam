package com.tregouet.occam.alg.scoring_dep.costs.functions.impl;

import com.tregouet.occam.alg.scoring_dep.costs.functions.IFunctionCoster;
import com.tregouet.occam.data.automata.IAutomaton;
import com.tregouet.occam.data.automata.transition_functions.transitions.IConjunctiveTransition;

public class NbOfBasicProductions implements IFunctionCoster {
	
	public static final NbOfBasicProductions INSTANCE = new NbOfBasicProductions();
	private IAutomaton automaton = null;
	
	private NbOfBasicProductions() {
	}

	@Override
	public IFunctionCoster input(IAutomaton automaton) {
		this.automaton = automaton;
		return this;
	}

	@Override
	public void setCost() {
		int nbOfBasicProductions = 0;
		for (IConjunctiveTransition conjTransition : automaton.getConjunctiveTransitions()) {
			nbOfBasicProductions += conjTransition.howManyBasicProductions();
		}
		automaton.weigh((double) nbOfBasicProductions);
	}

}
