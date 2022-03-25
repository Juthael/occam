package com.tregouet.occam.alg.scoring_dep.costs.transitions.impl;

import com.tregouet.occam.alg.scoring_dep.costs.transitions.ITransitionCoster;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.ICostedTransition;

public class Costless implements ITransitionCoster {

	public static final Costless INSTANCE = new Costless();
	private ICostedTransition costed = null;
	
	private Costless() {
	}

	@Override
	public ITransitionCoster input(ICostedTransition costed) {
		this.costed = costed;
		return this;
	}

	@Override
	public void setCost() {
		costed.weigh(0.0);
	}

}
