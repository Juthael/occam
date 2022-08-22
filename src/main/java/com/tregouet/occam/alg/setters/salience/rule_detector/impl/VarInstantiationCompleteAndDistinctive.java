package com.tregouet.occam.alg.setters.salience.rule_detector.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.setters.salience.rule_detector.RuleDetector;
import com.tregouet.occam.data.representations.productions.IContextualizedProduction;
import com.tregouet.occam.data.representations.productions.IProduction;

public class VarInstantiationCompleteAndDistinctive implements RuleDetector {

	public static final VarInstantiationCompleteAndDistinctive INSTANCE = new VarInstantiationCompleteAndDistinctive();

	private VarInstantiationCompleteAndDistinctive() {
	}

	@Override
	public Boolean apply(List<Set<IContextualizedProduction>> values) {
		return (everyTransitionIsCompleteInstantiation(values) && everyInstantiationIsDistinctive(values));
	}

	private static boolean everyInstantiationIsDistinctive(List<Set<IContextualizedProduction>> setsOfContextualizedProds) {
		Set<Set<IProduction>> uniqueValues = new HashSet<>();
		for (Set<IContextualizedProduction> contextualizedProds : setsOfContextualizedProds) {
			Set<IProduction> value = new HashSet<>();
			for (IContextualizedProduction contextualizedProd : contextualizedProds)
				value.add(contextualizedProd.getUncontextualizedProduction());
			if (!uniqueValues.add(value))
				return false;
		}
		return true;
	}

	private static boolean everyTransitionIsCompleteInstantiation(List<Set<IContextualizedProduction>> values) {
		for (Set<IContextualizedProduction> value : values) {
			if (value.isEmpty() || instantiationIsIncomplete(value))
				return false;
		}
		return true;
	}

	private static boolean instantiationIsIncomplete(Set<IContextualizedProduction> value) {
		for (IProduction production : value) {
			if (production.isAlphaConversionProd())
				return true;
		}
		return false;
	}

}
