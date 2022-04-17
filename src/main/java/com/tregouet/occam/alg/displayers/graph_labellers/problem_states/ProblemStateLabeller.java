package com.tregouet.occam.alg.displayers.graph_labellers.problem_states;

import java.util.function.Function;

import com.tregouet.occam.data.problem_spaces.IProblemState;

public interface ProblemStateLabeller extends Function<IProblemState, String> {

}
