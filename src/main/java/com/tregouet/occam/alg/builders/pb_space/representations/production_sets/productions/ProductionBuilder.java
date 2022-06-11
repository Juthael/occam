package com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public interface ProductionBuilder
		extends BiFunction<IDenotation, IDenotation, Set<IContextualizedProduction>> {
	
	ProductionBuilder setUp(IConcept targetConcept);

}
