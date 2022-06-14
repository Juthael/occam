package com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.abstr_apps.impl;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.abstr_apps.AbstrAppLabeller;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.applications.IAbstractionApplication;
import com.tregouet.occam.data.problem_space.states.productions.IBasicProduction;

public class Conjunction implements AbstrAppLabeller {

	public static final Conjunction INSTANCE = new Conjunction();

	private Conjunction() {
	}

	@Override
	public String apply(IAbstractionApplication operator) {
		List<IBasicProduction> productions = operator.getArguments();
		Iterator<IBasicProduction> prodIte = productions.iterator();
		StringBuilder sB = new StringBuilder();
		while (prodIte.hasNext()) {
			sB.append(prodIte.next().toString());
			if (prodIte.hasNext())
				sB.append("∧");
		}
		return sB.toString();
	}

}
