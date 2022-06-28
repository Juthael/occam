package com.tregouet.occam.alg.builders.pb_space.concept_lattices;

import java.util.Collection;
import java.util.function.Function;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.denotations.DenotationBuilder;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;

@FunctionalInterface
public interface ConceptLatticeBuilder extends Function<Collection<IContextObject>, IConceptLattice> {

	public static DenotationBuilder getDenotationBuilder() {
		return BuildersAbstractFactory.INSTANCE.getDenotationBuilder();
	}

}
