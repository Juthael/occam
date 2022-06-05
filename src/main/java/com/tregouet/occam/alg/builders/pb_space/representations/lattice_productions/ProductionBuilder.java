package com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

@FunctionalInterface
public interface ProductionBuilder extends Function<IConceptLattice, Set<IContextualizedProduction>> {

	public static ProdBuilderFromDenotations prodBuilderFromDenotations() {
		return BuildersAbstractFactory.INSTANCE.getProdBuilderFromDenotations();
	}

}
