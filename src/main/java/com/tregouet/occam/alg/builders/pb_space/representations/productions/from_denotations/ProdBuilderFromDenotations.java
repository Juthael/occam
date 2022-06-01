package com.tregouet.occam.alg.builders.pb_space.representations.productions.from_denotations;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;

public interface ProdBuilderFromDenotations
		extends BiFunction<IDenotation, IDenotation, Set<IContextualizedProduction>> {
	
	ProdBuilderFromDenotations setUp(IConcept targetConcept);

}
