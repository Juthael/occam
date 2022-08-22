package com.tregouet.occam.alg.displayers.formatters.problem_transitions;

import java.util.function.Function;

import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;

public interface ProblemTransitionLabeller extends Function<AProblemStateTransition, String> {

}
