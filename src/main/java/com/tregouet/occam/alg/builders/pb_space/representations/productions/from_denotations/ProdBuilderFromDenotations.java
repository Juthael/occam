package com.tregouet.occam.alg.builders.pb_space.representations.productions.from_denotations;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.data.representations.concepts.denotations.IDenotation;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;

@FunctionalInterface
public interface ProdBuilderFromDenotations
		extends BiFunction<IDenotation, IDenotation, Set<IContextualizedProduction>> {

}
