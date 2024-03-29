package com.tregouet.occam.alg.setters.salience.impl;

import java.util.Set;

import com.tregouet.occam.alg.setters.salience.ProductionSalienceSetter;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;
import com.tregouet.occam.data.structures.representations.productions.Salience;

public class EverythingIsRule implements ProductionSalienceSetter {

	public static final EverythingIsRule INSTANCE = new EverythingIsRule();

	private EverythingIsRule() {
	}

	@Override
	public void accept(Set<IContextualizedProduction> productions) {
		for (IContextualizedProduction production : productions)
			production.setSalience(Salience.TRANSITION_RULE);
	}

	@Override
	public ProductionSalienceSetter setUp(IClassification classification) {
		return this;
	}

}
