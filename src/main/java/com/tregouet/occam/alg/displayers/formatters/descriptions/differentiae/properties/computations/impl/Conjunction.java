package com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.computations.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.computations.ComputationLabeller;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.structures.representations.productions.IBasicProduction;

public class Conjunction implements ComputationLabeller {

	public static final Conjunction INSTANCE = new Conjunction();

	private Conjunction() {
	}

	@Override
	public String apply(IComputation computation) {
		List<IBasicProduction> productions = new ArrayList<>();
		for (IBasicProduction production : computation.getOperator().getArguments()) {
			productions.add(production);
		}
		Iterator<IBasicProduction> prodIte = productions.iterator();
		StringBuilder sB = new StringBuilder();
		while (prodIte.hasNext()) {
			sB.append(prodIte.next().toString());
			if (prodIte.hasNext()) {
				sB.append("∧");
			}

		}
		return sB.toString();
	}

}
