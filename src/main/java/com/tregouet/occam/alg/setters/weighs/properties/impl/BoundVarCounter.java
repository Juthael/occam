package com.tregouet.occam.alg.setters.weighs.properties.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public class BoundVarCounter implements PropertyWeigher {

	public static final BoundVarCounter INSTANCE = new BoundVarCounter();

	private BoundVarCounter() {
	}

	@Override
	public void accept(IProperty property) {
		Set<AVariable> varInstantiatedByNonRedundantApp = new HashSet<>();
		for (IContextualizedProduction app : property.getProductions()) {
			if (!app.isAlphaConversion()) {
				varInstantiatedByNonRedundantApp.add(app.getVariable());
			}
		}
		property.setWeight((double) varInstantiatedByNonRedundantApp.size());
	}

}
