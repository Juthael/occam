package com.tregouet.occam.alg.calculators.costs.dep.transition_function;

import java.util.function.Function;

import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public interface ITransitionFunctionCostCalculator extends Function<ITransitionFunction, Double> {

}
