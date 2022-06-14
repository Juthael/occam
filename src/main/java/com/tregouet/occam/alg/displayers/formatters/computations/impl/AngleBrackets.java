package com.tregouet.occam.alg.displayers.formatters.computations.impl;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.alg.displayers.formatters.computations.ComputationLabeller;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;

public class AngleBrackets implements ComputationLabeller {

	public static final AngleBrackets INSTANCE = new AngleBrackets();

	private AngleBrackets() {
	}

	@Override
	public String apply(IComputation computation) {
		List<IProduction> productions = computation.getArguments();
		if (productions.size() == 1)
			return "<" + productions.get(0).toString() + ">";
		Iterator<IProduction> prodIte = productions.iterator();
		StringBuilder sB = new StringBuilder();
		sB.append("<");
		while (prodIte.hasNext()) {
			sB.append(prodIte.next().toString());
			if (prodIte.hasNext())
				sB.append(",");
		}
		sB.append(">");
		return sB.toString();
	}

}
