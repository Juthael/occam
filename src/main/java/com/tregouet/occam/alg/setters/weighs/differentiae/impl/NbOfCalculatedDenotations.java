package com.tregouet.occam.alg.setters.weighs.differentiae.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;

public class NbOfCalculatedDenotations implements DifferentiaeWeigher {

	public static final NbOfCalculatedDenotations INSTANCE = new NbOfCalculatedDenotations();

	private NbOfCalculatedDenotations() {
	}

	@Override
	public void accept(ADifferentiae differentiae) {
		Set<IDenotation> values = new HashSet<>();
		for (IProperty property : differentiae.getProperties()) {
			for (IApplication application : property.getApplications())
				values.add(application.getValue());
		}
		differentiae.setCoeffFreeWeight(values.size());
	}

}
