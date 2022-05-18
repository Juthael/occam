package com.tregouet.occam.alg.displayers.visualizers.problem_spaces;

import java.util.function.BiFunction;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public interface ProblemSpaceViz
		extends BiFunction<DirectedAcyclicGraph<IRepresentation, AProblemStateTransition>, String, String> {

}
