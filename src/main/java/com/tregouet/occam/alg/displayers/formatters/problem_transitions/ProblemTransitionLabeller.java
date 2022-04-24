package com.tregouet.occam.alg.displayers.formatters.problem_transitions;

import java.util.function.Function;

import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;

public interface ProblemTransitionLabeller extends Function<AProblemStateTransition, String> {

}
