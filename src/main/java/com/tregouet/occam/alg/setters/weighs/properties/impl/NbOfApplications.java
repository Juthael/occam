package com.tregouet.occam.alg.setters.weighs.properties.impl;

import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;

public class NbOfApplications implements PropertyWeigher {

	public static final NbOfApplications INSTANCE = new NbOfApplications();

	private NbOfApplications() {
	}

	@Override
	public void accept(IProperty property) {
		int weight = 0;
		for (IApplication application : property.getApplications()) {
			if (!application.isBlank())
				weight++;
		}
		property.setWeight(weight);
	}

}
