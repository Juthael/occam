package com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.salience_mapper;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.Salience;

public interface ProductionSalienceMapper 
	extends BiFunction<IClassification, Set<IContextualizedProduction>, Map<IContextualizedProduction, Salience>> {

}
