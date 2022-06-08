package com.tregouet.occam.alg.displayers.formatters.differentiae.properties.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;

public class AsProductionString implements PropertyLabeller {

	public static final AsProductionString INSTANCE = new AsProductionString();
	private static final String nL = System.lineSeparator();

	private AsProductionString() {
	}

	@Override
	public String apply(IProperty input) {
		StringBuilder sB = new StringBuilder();
		sB.append("{");
		Set<IProduction> productions = new HashSet<>();
		for (IContextualizedProduction app : input.getProductions())
			productions.add(app.getUncontextualizedProduction());
		Iterator<IProduction> prodIte = productions.iterator();
		while (prodIte.hasNext()) {
			sB.append(prodIte.next());
			if (prodIte.hasNext())
				sB.append(", " + nL);
		}
		sB.append("}");
		return sB.toString();
	}

}
