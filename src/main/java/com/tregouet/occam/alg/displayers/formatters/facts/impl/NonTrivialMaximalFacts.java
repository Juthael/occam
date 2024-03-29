package com.tregouet.occam.alg.displayers.formatters.facts.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayer;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.structures.representations.evaluation.facts.IFact;

public class NonTrivialMaximalFacts implements FactDisplayer {

	public static final NonTrivialMaximalFacts INSTANCE = new NonTrivialMaximalFacts();

	private NonTrivialMaximalFacts() {
	}

	@Override
	public SortedSet<String> apply(Set<IFact> facts) {
		TreeSet<String> stringFacts = new TreeSet<>();
		List<IFact> nonTrivialMaxFacts = new ArrayList<>();
		for (IFact fact : facts) {
			if (!isTrivial(fact)) {
				boolean isMaximal = true;
				ListIterator<IFact> nonTrivialMaxFactsIte = nonTrivialMaxFacts.listIterator();
				while (isMaximal && nonTrivialMaxFactsIte.hasNext()) {
					Integer comparison = fact.compareTo(nonTrivialMaxFactsIte.next());
					if (comparison != null) {
						if (comparison > 0)
							nonTrivialMaxFactsIte.remove();
						else if (comparison < 0)
							isMaximal = false;
					}
				}
				if (isMaximal)
					nonTrivialMaxFacts.add(fact);
			}
		}
		for (IFact fact : nonTrivialMaxFacts) {
			stringFacts.add(fact.asLambda().toString());
		}
		return stringFacts;
	}

	private boolean isTrivial(IFact fact) {
		if (fact.asList().size() == 1) {
			IAbstractionApplication app = fact.asList().get(0);
			if (app.isEpsilonOperator())
				return true;
		}
		return false;
	}

}
