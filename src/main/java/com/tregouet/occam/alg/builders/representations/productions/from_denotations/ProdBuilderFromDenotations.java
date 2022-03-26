package com.tregouet.occam.alg.builders.representations.productions.from_denotations;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.representations.concepts.IDenotation;

@FunctionalInterface
public interface ProdBuilderFromDenotations extends BiFunction<IDenotation, IDenotation, Set<IContextualizedProduction>> {

}
