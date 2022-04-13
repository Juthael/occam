package com.tregouet.occam.alg.displayers.problem_transitions;

import java.util.function.Function;

import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;

public interface ProblemTransitionDisplayer extends Function<AProblemStateTransition, String> {

}
