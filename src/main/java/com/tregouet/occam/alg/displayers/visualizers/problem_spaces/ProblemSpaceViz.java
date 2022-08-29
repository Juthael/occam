package com.tregouet.occam.alg.displayers.visualizers.problem_spaces;

import java.util.function.BiFunction;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.modules.sorting.transitions.AProblemStateTransition;
import com.tregouet.occam.data.structures.representations.IRepresentation;

public interface ProblemSpaceViz
		extends BiFunction<DirectedAcyclicGraph<IRepresentation, AProblemStateTransition>, String, String> {

}
