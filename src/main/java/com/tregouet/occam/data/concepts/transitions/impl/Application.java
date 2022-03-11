package com.tregouet.occam.data.concepts.transitions.impl;

import com.tregouet.occam.data.concepts.transitions.IApplication;

public class Application extends ConceptTransition implements IApplication {

	public Application(ConceptTransitionIC inputConfig, ConceptTransitionOIC outputInternConfig) {
		super(inputConfig, outputInternConfig);
	}
	
	@Override
	public boolean isRedundant() {
		return getInputConfiguration().getInputSymbol().isRedundant();
	}	

}
