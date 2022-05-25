package com.tregouet.occam.alg.builders.pb_space.representations.productions;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.data.problem_space.states.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;

@FunctionalInterface
public interface ProductionBuilder extends Function<IConceptLattice, Set<IContextualizedProduction>> {

	public static ProdBuilderFromDenotations prodBuilderFromDenotations() {
		return BuildersAbstractFactory.INSTANCE.getProdBuilderFromDenotations();
	}

}
