package com.tregouet.occam.alg.builders.representations.production_sets.productions;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;

public interface ProductionBuilder
		extends BiFunction<IDenotation, IDenotation, Set<IContextualizedProduction>> {

	ProductionBuilder setUp(IConcept targetConcept);

}
