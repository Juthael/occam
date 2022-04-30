package com.tregouet.occam.alg.builders.problem_spaces.ranker;

import java.util.function.Consumer;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;

public interface ProblemTransitionRanker extends Consumer<AProblemStateTransition> {
	
	/**
	 * 
	 * @param problemSpace - must have been transitively reduced
	 * @return
	 */
	public ProblemTransitionRanker setUp(DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemSpace);

}
