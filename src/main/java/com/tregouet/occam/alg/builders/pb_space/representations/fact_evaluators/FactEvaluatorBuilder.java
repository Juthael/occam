package com.tregouet.occam.alg.builders.pb_space.representations.fact_evaluators;

import java.util.function.Function;

import com.tregouet.occam.data.problem_space.states.evaluation.IFactEvaluator;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;

public interface FactEvaluatorBuilder extends Function<IRepresentationTransitionFunction, IFactEvaluator> {

}
