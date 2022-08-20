package com.tregouet.occam.alg.displayers.formatters.differentiae.properties.impl;

import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;

public class CurlyBracketsWithWeight implements PropertyLabeller {

	public static final CurlyBracketsWithWeight INSTANCE = new CurlyBracketsWithWeight();
	private static final String nL = System.lineSeparator();

	private CurlyBracketsWithWeight() {
	}

	@Override
	public String apply(IProperty property) {
		StringBuilder sB = new StringBuilder();
		sB.append("{");
		Set<IComputation> computations = property.getComputations();
		Iterator<IComputation> appIte = computations.iterator();
		while (appIte.hasNext()) {
			sB.append(PropertyLabeller.computationLabeller().apply(appIte.next()));
			if (appIte.hasNext())
				sB.append("," + nL);
		}
		sB.append("}, " + Double.toString(property.weight()));
		return sB.toString();
	}

}
