package com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.salience_mapper;

import java.util.Set;
import java.util.function.Consumer;

import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public interface ProductionSalienceSetter 
	extends Consumer<Set<IContextualizedProduction>> {
	
	ProductionSalienceSetter setUp(IClassification classification);

}
