package com.tregouet.occam.alg.builders.representations.fact_evaluators.impl;

import java.util.Set;
import java.util.stream.Collectors;

import com.tregouet.occam.alg.builders.representations.fact_evaluators.FactEvaluatorBuilder;
import com.tregouet.occam.data.representations.evaluation.IFactEvaluator;
import com.tregouet.occam.data.representations.evaluation.impl.FactEvaluator;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.Salience;
import com.tregouet.occam.data.representations.transitions.TransitionType;
import com.tregouet.occam.data.representations.transitions.impl.RepresentationTransitionFunction;

public class SalienceAwareFEBuilder implements FactEvaluatorBuilder {

	public static final SalienceAwareFEBuilder INSTANCE = new SalienceAwareFEBuilder();

	private static Set<IConceptTransition> filter(Set<IConceptTransition> transitions) {
		return transitions.stream().filter(t -> !isANonSalientApplication(t)).collect(Collectors.toSet());
	}

	private static boolean isANonSalientApplication(IConceptTransition transition) {
		if (transition.type() == TransitionType.APPLICATION
				&& (transition.getSalience() == Salience.HIDDEN || transition.getSalience() == Salience.REDUNDANT))
			return true;
		return false;
	}

	private SalienceAwareFEBuilder() {
	}

	@Override
	public IFactEvaluator apply(IRepresentationTransitionFunction transitionFunction) {
		IFactEvaluator factEvaluator = new FactEvaluator();
		IRepresentationTransitionFunction filteredtransFunc = new RepresentationTransitionFunction(
				filter(transitionFunction.getTransitions()));
		factEvaluator.set(filteredtransFunc);
		return factEvaluator;
	}

}
