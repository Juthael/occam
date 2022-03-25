package com.tregouet.occam.alg.builders.representations.concept_lattices;

import java.util.Collection;

import com.google.common.base.Function;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;

@FunctionalInterface
public interface IConceptLatticeBuilder extends Function<Collection<IContextObject>, IConceptLattice> {

}
