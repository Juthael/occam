package com.tregouet.occam.alg.setters.salience.rule_detector;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

@FunctionalInterface
public interface RuleDetector extends Function<List<Set<IContextualizedProduction>>, Boolean> {

}
