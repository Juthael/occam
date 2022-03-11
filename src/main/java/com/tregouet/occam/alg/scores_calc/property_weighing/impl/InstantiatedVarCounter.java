package com.tregouet.occam.alg.scores_calc.property_weighing.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.scores_calc.property_weighing.IPropertyWeigher;
import com.tregouet.occam.data.concepts.properties.IProperty;
import com.tregouet.occam.data.concepts.transitions.IApplication;
import com.tregouet.occam.data.languages.generic.AVariable;

public class InstantiatedVarCounter implements IPropertyWeigher {

	public static final InstantiatedVarCounter INSTANCE = new InstantiatedVarCounter();
	
	private InstantiatedVarCounter() {
	}
	
	@Override
	public void setCost(IProperty property) {
		Set<AVariable> varInstantiatedByNonRedundantApp = new HashSet<>();
		for (IApplication app : property.getApplications()) {
			if (!app.isRedundant()) {
				varInstantiatedByNonRedundantApp.add(app.getInputConfiguration().getInputStackSymbol());
			}
		}
		property.setWeight((double) varInstantiatedByNonRedundantApp.size());
	}

}
