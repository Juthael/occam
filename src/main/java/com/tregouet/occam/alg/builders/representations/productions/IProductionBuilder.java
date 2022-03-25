package com.tregouet.occam.alg.builders.representations.productions;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;

@FunctionalInterface
public interface IProductionBuilder extends Function<IConceptLattice, Set<IContextualizedProduction>> {

}
