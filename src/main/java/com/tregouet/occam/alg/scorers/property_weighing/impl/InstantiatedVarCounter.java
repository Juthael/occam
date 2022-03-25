package com.tregouet.occam.alg.scorers.property_weighing.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.scorers.property_weighing.IPropertyWeigher;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.representations.properties.IProperty;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;

public class InstantiatedVarCounter implements IPropertyWeigher {

	public static final InstantiatedVarCounter INSTANCE = new InstantiatedVarCounter();
	
	private InstantiatedVarCounter() {
	}
	
	@Override
	public double weigh(IProperty property) {
		Set<AVariable> varInstantiatedByNonRedundantApp = new HashSet<>();
		for (IApplication app : property.getApplications()) {
			if (!app.isRedundant()) {
				varInstantiatedByNonRedundantApp.add(app.getInputConfiguration().getPoppedStackSymbol());
			}
		}
		return (double) varInstantiatedByNonRedundantApp.size();
	}

}
