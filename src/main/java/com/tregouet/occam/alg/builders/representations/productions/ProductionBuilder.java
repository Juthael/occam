package com.tregouet.occam.alg.builders.representations.productions;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;

@FunctionalInterface
public interface ProductionBuilder extends Function<IConceptLattice, Set<IContextualizedProduction>> {

	public static ProdBuilderFromDenotations prodBuilderFromDenotations() {
		return GeneratorsAbstractFactory.INSTANCE.getProdBuilderFromDenotations();
	}

}
