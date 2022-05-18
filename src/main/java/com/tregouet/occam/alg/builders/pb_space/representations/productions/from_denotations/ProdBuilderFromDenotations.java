package com.tregouet.occam.alg.builders.pb_space.representations.productions.from_denotations;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.data.problem_space.states.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;

@FunctionalInterface
public interface ProdBuilderFromDenotations
		extends BiFunction<IDenotation, IDenotation, Set<IContextualizedProduction>> {

}
