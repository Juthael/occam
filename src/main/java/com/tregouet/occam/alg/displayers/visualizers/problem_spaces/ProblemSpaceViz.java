package com.tregouet.occam.alg.displayers.visualizers.problem_spaces;

import java.util.function.BiFunction;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.displayers.visualizers.VisualizersAbstractFactory;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;

public interface ProblemSpaceViz
		extends BiFunction<DirectedAcyclicGraph<IProblemState, AProblemStateTransition>, String, String> {
	
	public static int getMaxNbOfProblemStatesDisplayed() {
		return VisualizersAbstractFactory.INSTANCE.getMaxNbOfProblemStatesDisplayed();
	}

}
