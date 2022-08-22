package com.tregouet.occam.alg.displayers.formatters.differentiae.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Iterator;

import com.tregouet.occam.alg.displayers.formatters.differentiae.DifferentiaeLabeller;
import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.IProperty;

public class PropertiesThenWeight implements DifferentiaeLabeller {

	public static final PropertiesThenWeight INSTANCE = new PropertiesThenWeight();
	private static final MathContext mathContext = new MathContext(3);

	private PropertiesThenWeight() {
	}

	@Override
	public String apply(ADifferentiae differentiae) {
		String nL = System.lineSeparator();
		Iterator<IProperty> propIte = differentiae.getProperties().iterator();
		StringBuilder sBProp = new StringBuilder();
		while (propIte.hasNext()) {
			IProperty nextProp = propIte.next();
			if (!nextProp.isBlank()) {
				if (sBProp.length() > 0)
					sBProp.append(nL);
				sBProp.append(DifferentiaeLabeller.getPropertyDisplayer().apply(nextProp));
			}
		}
		return "weight : " + weightAsString(differentiae.weight()) + nL + sBProp.toString();
	}

	private static String weightAsString(double weight) {
		return new BigDecimal(weight).round(mathContext).toString();
	}

}
