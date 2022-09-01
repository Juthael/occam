package com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;

public class CurlyBracketsNoIdentity implements PropertyLabeller {

	public static final CurlyBracketsNoIdentity INSTANCE = new CurlyBracketsNoIdentity();
	private static final String nL = System.lineSeparator();
	private static final MathContext mathContext = new MathContext(3);

	private CurlyBracketsNoIdentity() {
	}

	@Override
	public String apply(IProperty property) {
		StringBuilder sB = new StringBuilder();
		sB.append("{");
		Set<IComputation> computations = new HashSet<>();
		for (IComputation computation : property.getComputations()) {
			if (!computation.isIdentity())
				computations.add(computation);
		}
		Iterator<IComputation> appIte = computations.iterator();
		while (appIte.hasNext()) {
			sB.append(PropertyLabeller.computationLabeller().apply(appIte.next()));
			if (appIte.hasNext())
				sB.append("," + nL);
		}
		sB.append("},");
		if (property.weight() != null) {
			sB.append(", " + new BigDecimal(property.weight()).round(mathContext).toString());
		}
		return sB.toString();
	}

}
