package com.tregouet.occam.alg.builders.representations.production_sets.productions;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.representations.productions.IContextualizedProduction;

public interface ProductionBuilder
		extends BiFunction<IDenotation, IDenotation, Set<IContextualizedProduction>> {

	ProductionBuilder setUp(IConcept targetConcept);

}
