package com.tregouet.occam.alg.setters.weighs.properties.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.representations.properties.IProperty;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;

public class InstantiatedVarCounter implements PropertyWeigher {

	public static final InstantiatedVarCounter INSTANCE = new InstantiatedVarCounter();
	
	private InstantiatedVarCounter() {
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
