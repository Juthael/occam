package com.tregouet.occam.alg.builders.pb_space.representations.production_sets.salience_setter;

import java.util.Set;
import java.util.function.Consumer;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.salience_setter.rule_detector.RuleDetector;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public interface ProductionSalienceSetter
	extends Consumer<Set<IContextualizedProduction>> {

	ProductionSalienceSetter setUp(IClassification classification);
	
	public static RuleDetector ruleDetector() {
		return BuildersAbstractFactory.INSTANCE.getRuleDetector();
	}

}
