package com.tregouet.occam.alg.builders.pb_space.representations.classification_productions;

import java.util.Set;

import com.google.common.base.Function;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.salience_mapper.ProductionSalienceMapper;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.impl.ClassificationProductions;

public interface ClassificationProductionSetBuilder 
	extends Function<IClassification, ClassificationProductions> {
	
	public static ProductionSalienceMapper productionSalienceMapper() {
		return BuildersAbstractFactory.INSTANCE.getProductionSalienceMapper();
	}
	
	ClassificationProductionSetBuilder setUp(Set<IContextualizedProduction> latticeProductions);

}
