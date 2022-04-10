package com.tregouet.occam.alg.setters.weighs.properties.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.representations.descriptions.properties.IProperty;
import com.tregouet.occam.data.representations.transitions.IApplication;

public class BoundVarCounter implements PropertyWeigher {

	public static final BoundVarCounter INSTANCE = new BoundVarCounter();
	
	private BoundVarCounter() {
	}
	
	@Override
	public void accept(IProperty property) {
		Set<AVariable> varInstantiatedByNonRedundantApp = new HashSet<>();
		for (IApplication app : property.getApplications()) {
			if (!app.isRedundant()) {
				varInstantiatedByNonRedundantApp.add(app.getInputConfiguration().getRequiredStackSymbol());
			}
		}
		property.setWeight((double) varInstantiatedByNonRedundantApp.size());
	}

}
