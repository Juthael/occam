package com.tregouet.occam.alg.displayers.differentiae.impl;

import java.util.Iterator;

import com.tregouet.occam.alg.displayers.differentiae.DifferentiaeDisplayer;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.descriptions.properties.IProperty;

public class PropertiesThenWeight implements DifferentiaeDisplayer {
	
	public static final PropertiesThenWeight INSTANCE = new PropertiesThenWeight();
	
	private PropertiesThenWeight() {
	}

	@Override
	public String apply(AbstractDifferentiae differentiae) {
		StringBuilder sB = new StringBuilder();
		String nL = System.lineSeparator();
		Iterator<IProperty> propIte = differentiae.getProperties().iterator();
		while (propIte.hasNext()) {
			sB.append(DifferentiaeDisplayer.getPropertyDisplayer().apply(propIte.next()));
			sB.append(nL);
		}
		sB.append(differentiae.weight().toString());
		return null;
	}

}
