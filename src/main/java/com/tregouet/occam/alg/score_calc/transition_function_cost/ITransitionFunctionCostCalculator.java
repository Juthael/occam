package com.tregouet.occam.alg.score_calc.transition_function_cost;

import java.util.function.Function;

import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;

public interface ITransitionFunctionCostCalculator extends Function<ITransitionFunction, Double> {

}
