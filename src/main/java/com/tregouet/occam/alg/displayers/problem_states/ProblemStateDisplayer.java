package com.tregouet.occam.alg.displayers.problem_states;

import java.util.function.Function;

import com.tregouet.occam.data.problem_spaces.IProblemState;

public interface ProblemStateDisplayer extends Function<IProblemState, String> {

}
