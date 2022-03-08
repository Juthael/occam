package com.tregouet.occam.alg.scoring.costs.transitions.impl;

import com.tregouet.occam.alg.scoring.costs.transitions.ITransitionCoster;
import com.tregouet.occam.data.automata.transitions.IBasicOperator;
import com.tregouet.occam.data.automata.transitions.ICostedTransition;

public class IfPropertyThenOne implements ITransitionCoster {

	public static final IfPropertyThenOne INSTANCE = new IfPropertyThenOne();
	private ICostedTransition costed = null;
	
	private IfPropertyThenOne() {
	}

	@Override
	public ITransitionCoster input(ICostedTransition costed) {
		this.costed = costed;
		return this;
	}

	@Override
	public void setCost() {
		if (costed instanceof IBasicOperator && ((IBasicOperator) costed).isBlank())
			costed.setCost(0.0);
		else costed.setCost(1.0);
	}

}
