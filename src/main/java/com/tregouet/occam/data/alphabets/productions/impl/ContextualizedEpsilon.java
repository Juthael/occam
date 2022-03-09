package com.tregouet.occam.data.alphabets.productions.impl;

import com.tregouet.occam.data.alphabets.productions.Input;
import com.tregouet.occam.data.preconcepts.IDenotation;

public class ContextualizedEpsilon extends ContextualizedProduction implements Input {

	private static final long serialVersionUID = -6093921194463496575L;
	
	public ContextualizedEpsilon(IDenotation speciesDenotation, IDenotation genusDenotation) {
		super(speciesDenotation, genusDenotation, Epsilon.INSTANCE);
	}

}
