package com.tregouet.occam.alg.builders.representations.fact_evaluators.impl;

import com.tregouet.occam.alg.builders.representations.fact_evaluators.FactEvaluatorBuilder;
import com.tregouet.occam.data.representations.evaluation.IFactEvaluator;
import com.tregouet.occam.data.representations.evaluation.impl.FactEvaluator;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;

public class SalienceBlindFEBuilder implements FactEvaluatorBuilder {

	public static final SalienceBlindFEBuilder INSTANCE = new SalienceBlindFEBuilder();

	private SalienceBlindFEBuilder() {
	}

	@Override
	public IFactEvaluator apply(IRepresentationTransitionFunction transitionFunction) {
		IFactEvaluator factEvaluator = new FactEvaluator();
		factEvaluator.set(transitionFunction);
		return factEvaluator;
	}

}
