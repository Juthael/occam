package com.tregouet.occam.alg.builders.pb_space.representations.classification_productions;

import java.util.Set;

import com.google.common.base.Function;
import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.salience_mapper.ProductionSalienceSetter;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public interface ProductionsBuilder 
	extends Function<IClassification, Set<IContextualizedProduction>> {
	
	public static ProductionSalienceSetter productionSalienceSetter() {
		return BuildersAbstractFactory.INSTANCE.getProductionSalienceSetter();
	}

}
