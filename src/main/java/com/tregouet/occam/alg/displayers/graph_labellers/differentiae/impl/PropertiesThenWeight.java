package com.tregouet.occam.alg.displayers.graph_labellers.differentiae.impl;

import java.util.Iterator;

import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.DifferentiaeLabeller;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.descriptions.properties.IProperty;

public class PropertiesThenWeight implements DifferentiaeLabeller {

	public static final PropertiesThenWeight INSTANCE = new PropertiesThenWeight();

	private PropertiesThenWeight() {
	}

	@Override
	public String apply(AbstractDifferentiae differentiae) {
		StringBuilder sB = new StringBuilder();
		String nL = System.lineSeparator();
		sB.append(differentiae.weight().toString() + nL);
		Iterator<IProperty> propIte = differentiae.getProperties().iterator();
		while (propIte.hasNext()) {
			sB.append(DifferentiaeLabeller.getPropertyDisplayer().apply(propIte.next()));
			if (propIte.hasNext())
				sB.append(nL);
		}
		return sB.toString();
	}

}
