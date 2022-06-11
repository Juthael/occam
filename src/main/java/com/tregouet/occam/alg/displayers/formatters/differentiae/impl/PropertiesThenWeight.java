package com.tregouet.occam.alg.displayers.formatters.differentiae.impl;

import java.util.Iterator;

import com.tregouet.occam.alg.displayers.formatters.differentiae.DifferentiaeLabeller;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;

public class PropertiesThenWeight implements DifferentiaeLabeller {

	public static final PropertiesThenWeight INSTANCE = new PropertiesThenWeight();

	private PropertiesThenWeight() {
	}

	@Override
	public String apply(ADifferentiae differentiae) {
		StringBuilder sB = new StringBuilder();
		String nL = System.lineSeparator();
		sB.append("weight : " + weightAsString(differentiae.weight()) + nL);
		Iterator<IProperty> propIte = differentiae.getProperties().iterator();
		while (propIte.hasNext()) {
			sB.append(DifferentiaeLabeller.getPropertyDisplayer().apply(propIte.next()));
			if (propIte.hasNext())
				sB.append(nL);
		}
		return sB.toString();
	}

	private static String weightAsString(double weight) {
		return Integer.toString((int) weight);
	}

}
