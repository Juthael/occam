package com.tregouet.occam.data.representations.transitions.impl;

import com.tregouet.occam.data.representations.transitions.IApplication;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.transitions.TransitionType;

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
