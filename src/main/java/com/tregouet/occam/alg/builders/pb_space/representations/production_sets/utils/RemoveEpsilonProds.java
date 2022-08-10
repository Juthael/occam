package com.tregouet.occam.alg.builders.pb_space.representations.production_sets.utils;

import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public interface RemoveEpsilonProds {

	public static Set<IContextualizedProduction> in(Set<IContextualizedProduction> productions){
		Iterator<IContextualizedProduction> prodIte = productions.iterator();
		while (prodIte.hasNext()) {
			IContextualizedProduction nextProd = prodIte.next();
			if (nextProd.isEpsilon())
				prodIte.remove();
		}
		return productions;
	}

}