package com.tregouet.occam.data.problem_space.states.productions.impl;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public class ContextualizedEpsilonProd extends ContextualizedProd implements IContextualizedProduction {

	private static final long serialVersionUID = -6093921194463496575L;

	public ContextualizedEpsilonProd(IDenotation speciesDenotation, IDenotation genusDenotation) {
		super(speciesDenotation, genusDenotation, EpsilonProd.INSTANCE);
	}

}
