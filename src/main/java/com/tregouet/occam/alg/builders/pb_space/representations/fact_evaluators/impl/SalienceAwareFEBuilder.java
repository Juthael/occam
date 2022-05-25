package com.tregouet.occam.alg.builders.pb_space.representations.fact_evaluators.impl;

import java.util.Set;
import java.util.stream.Collectors;

import com.tregouet.occam.alg.builders.pb_space.representations.fact_evaluators.FactEvaluatorBuilder;
import com.tregouet.occam.data.problem_space.states.evaluation.IFactEvaluator;
import com.tregouet.occam.data.problem_space.states.evaluation.impl.FactEvaluator;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.Salience;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.impl.RepresentationTransitionFunction;

public class SalienceAwareFEBuilder implements FactEvaluatorBuilder {

	public static final SalienceAwareFEBuilder INSTANCE = new SalienceAwareFEBuilder();

	private SalienceAwareFEBuilder() {
	}

	private static Set<IConceptTransition> filter(Set<IConceptTransition> transitions) {
		return transitions.stream().filter(t -> !isANonSalientApplication(t)).collect(Collectors.toSet());
	}

	private static boolean isANonSalientApplication(IConceptTransition transition) {
		if (transition.type() == TransitionType.APPLICATION
				&& (transition.getSalience() == Salience.HIDDEN || transition.getSalience() == Salience.REDUNDANT))
			return true;
		return false;
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
