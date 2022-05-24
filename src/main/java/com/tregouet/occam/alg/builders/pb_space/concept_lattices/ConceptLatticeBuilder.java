package com.tregouet.occam.alg.builders.pb_space.concept_lattices;

import java.util.Collection;

import com.google.common.base.Function;
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.denotations.DenotationBuilder;
import com.tregouet.occam.data.problem_space.states.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;

@FunctionalInterface
public interface ConceptLatticeBuilder extends Function<Collection<IContextObject>, IConceptLattice> {

	public static DenotationBuilder getDenotationBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getDenotationBuilder();
	}

}