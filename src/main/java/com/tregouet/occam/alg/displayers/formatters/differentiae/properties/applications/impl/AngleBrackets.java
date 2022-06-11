package com.tregouet.occam.alg.displayers.formatters.differentiae.properties.applications.impl;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.applications.ApplicationLabeller;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;

public class AngleBrackets implements ApplicationLabeller {
	
	public static final AngleBrackets INSTANCE = new AngleBrackets();
	
	private AngleBrackets() {
	}

	@Override
	public String apply(IApplication application) {
		List<IProduction> productions = application.getArguments();
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
