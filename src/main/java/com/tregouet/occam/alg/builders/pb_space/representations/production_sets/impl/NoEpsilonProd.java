package com.tregouet.occam.alg.builders.pb_space.representations.production_sets.impl;

import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.ProductionSetBuilder;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public class NoEpsilonProd extends BuildFromScratch implements ProductionSetBuilder {
	
	protected Set<IContextualizedProduction> complyWithAdditionalConstraint(Set<IContextualizedProduction> productions){
		return removeEpsilonProduction(productions);
	}
	
	private Set<IContextualizedProduction> removeEpsilonProduction(Set<IContextualizedProduction> productions){
		Iterator<IContextualizedProduction> prodIte = productions.iterator();
		while (prodIte.hasNext()) {
			IContextualizedProduction nextProd = prodIte.next();
			if (nextProd.isEpsilon())
				prodIte.remove();
		}
		return productions;
	}

}
