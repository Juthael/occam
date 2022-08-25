package com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.properties.computations.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.properties.computations.ComputationLabeller;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.structures.representations.productions.IBasicProduction;

public class ConjunctionNoIdentity implements ComputationLabeller {

	public static final ConjunctionNoIdentity INSTANCE = new ConjunctionNoIdentity();

	private ConjunctionNoIdentity() {
	}

	@Override
	public String apply(IComputation computation) {
		List<IBasicProduction> productions = new ArrayList<>();
		for (IBasicProduction production : computation.getOperator().getArguments()) {
			if (!production.isIdentityProd())
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
