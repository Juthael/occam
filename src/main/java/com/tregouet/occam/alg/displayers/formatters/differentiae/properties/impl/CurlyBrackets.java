package com.tregouet.occam.alg.displayers.formatters.differentiae.properties.impl;

import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;

public class CurlyBrackets implements PropertyLabeller {

	public static final CurlyBrackets INSTANCE = new CurlyBrackets();
	private static final String nL = System.lineSeparator();

	private CurlyBrackets() {
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
		sB.append("}");
		return sB.toString();
	}

}
