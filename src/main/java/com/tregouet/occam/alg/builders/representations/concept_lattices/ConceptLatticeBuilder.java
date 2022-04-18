package com.tregouet.occam.alg.builders.representations.concept_lattices;

import java.util.Collection;

import com.google.common.base.Function;
import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.IDenotationBuilder;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;

@FunctionalInterface
public interface ConceptLatticeBuilder extends Function<Collection<IContextObject>, IConceptLattice> {

	public static IDenotationBuilder denotationBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getDenotationBuilder();
	}

}
