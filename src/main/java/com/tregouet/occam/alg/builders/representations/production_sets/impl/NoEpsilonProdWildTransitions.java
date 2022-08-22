package com.tregouet.occam.alg.builders.representations.production_sets.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.representations.production_sets.ProductionSetBuilder;
import com.tregouet.occam.alg.builders.representations.production_sets.utils.RemoveEpsilonProds;
import com.tregouet.occam.data.representations.productions.IContextualizedProduction;

public class NoEpsilonProdWildTransitions extends BuildFromScratchWildTransitions implements ProductionSetBuilder {

	@Override
	protected Set<IContextualizedProduction> complyWithAdditionalConstraint(Set<IContextualizedProduction> productions){
		return RemoveEpsilonProds.in(productions);
	}

}
