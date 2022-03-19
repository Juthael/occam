package com.tregouet.occam.data.representations.properties.transitions.impl;

import com.tregouet.occam.data.representations.properties.transitions.IApplication;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.properties.transitions.TransitionType;

public class Application extends ConceptTransition implements IApplication {

	public Application(IConceptTransitionIC inputConfig, IConceptTransitionOIC outputInternConfig) {
		super(inputConfig, outputInternConfig);
	}
	
	@Override
	public boolean isRedundant() {
		return getInputConfiguration().getInputSymbol().isRedundant();
	}

	@Override
	public TransitionType type() {
		return TransitionType.APPLICATION;
	}

}
