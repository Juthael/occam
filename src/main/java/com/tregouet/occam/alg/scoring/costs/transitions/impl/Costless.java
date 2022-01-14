package com.tregouet.occam.alg.scoring.costs.transitions.impl;

import com.tregouet.occam.alg.scoring.costs.transitions.ITransitionCoster;
import com.tregouet.occam.data.abstract_machines.transition_rules.ICostedTransition;

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
		costed.setCost(0.0);
	}

}
