package com.tregouet.occam.data.languages.specific.impl;

import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.languages.specific.IContextualizedProduction;

public class ContextualizedEpsilon extends ContextualizedProduction implements IContextualizedProduction {

	private static final long serialVersionUID = -6093921194463496575L;
	
	public ContextualizedEpsilon(IDenotation speciesDenotation, IDenotation genusDenotation) {
		super(speciesDenotation, genusDenotation, Epsilon.INSTANCE);
	}

}
