package com.tregouet.occam.alg.setters.salience.rule_detector.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.setters.salience.rule_detector.RuleDetector;
import com.tregouet.occam.data.structures.representations.productions.IBasicProduction;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;
import com.tregouet.occam.data.structures.representations.productions.IProduction;

public class VarInstantiationCompleteAndDistinctive implements RuleDetector {

	public static final VarInstantiationCompleteAndDistinctive INSTANCE = new VarInstantiationCompleteAndDistinctive();

	private VarInstantiationCompleteAndDistinctive() {
	}

	@Override
	public Boolean apply(List<Set<IContextualizedProduction>> values) {
		return (everyTransitionIsCompleteInstantiation(values) && everyInstantiationIsDistinctive(values));
	}

	private static boolean everyInstantiationIsDistinctive(List<Set<IContextualizedProduction>> setsOfContextualizedProds) {
		List<Set<IBasicProduction>> uniqueValues = new ArrayList<>();
		for (Set<IContextualizedProduction> contextualizedProds : setsOfContextualizedProds) {
			Set<IBasicProduction> value = new HashSet<>();
			for (IContextualizedProduction contextualizedProd : contextualizedProds)
				value.add(contextualizedProd.getUncontextualizedProduction());
			for (Set<IBasicProduction> uniqueValue : uniqueValues) {
				if (!productionSetsAreUncomparable(value, uniqueValue))
					return false;
			}
			uniqueValues.add(value);
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
	
	private static boolean productionSetsAreUncomparable(Set<IBasicProduction> set1, Set<IBasicProduction> set2) {
		for (IBasicProduction prodSet1 : set1) {
			boolean prodSet1Incomparable = true;
			for (IBasicProduction prodSet2 : set2) {
				if (prodSet1.compareTo(prodSet2) != null)
					prodSet1Incomparable = false;
			}
			if (prodSet1Incomparable)
				return true;
		}
		return false;
	}

}
