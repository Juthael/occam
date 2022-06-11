package com.tregouet.occam.alg.builders.pb_space.representations.production_sets;

import java.util.Set;

import com.google.common.base.Function;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.salience_setter.ProductionSalienceSetter;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public interface ProductionSetBuilder
	extends Function<IClassification, Set<IContextualizedProduction>> {

	public static ProductionBuilder productionBuilder() {
		return BuildersAbstractFactory.INSTANCE.getProductionBuilder();
	}

	public static ProductionSalienceSetter productionSalienceSetter() {
		return BuildersAbstractFactory.INSTANCE.getProductionSalienceSetter();
	}

}
