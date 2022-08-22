package com.tregouet.occam.data.representations.productions.impl;

import com.tregouet.occam.data.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.representations.productions.IContextualizedProduction;

public class ContextualizedEpsilonProd extends ContextualizedProd implements IContextualizedProduction {

	private static final long serialVersionUID = -6093921194463496575L;

	public ContextualizedEpsilonProd(IDenotation speciesDenotation, IDenotation genusDenotation) {
		super(speciesDenotation, genusDenotation, EpsilonProd.INSTANCE);
	}

}
