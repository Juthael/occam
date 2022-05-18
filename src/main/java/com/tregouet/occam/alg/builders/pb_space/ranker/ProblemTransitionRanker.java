package com.tregouet.occam.alg.builders.pb_space.ranker;

import java.util.function.Consumer;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public interface ProblemTransitionRanker extends Consumer<AProblemStateTransition> {
	
	/**
	 * 
	 * @param problemSpace - must have been transitively reduced
	 * @return
	 */
	public ProblemTransitionRanker setUp(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemSpace);

}
