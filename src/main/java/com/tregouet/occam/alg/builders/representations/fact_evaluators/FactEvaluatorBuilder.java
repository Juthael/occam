package com.tregouet.occam.alg.builders.representations.fact_evaluators;

import java.util.function.Function;

import com.tregouet.occam.data.representations.evaluation.IFactEvaluator;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;

public interface FactEvaluatorBuilder extends Function<IRepresentationTransitionFunction, IFactEvaluator> {

}
