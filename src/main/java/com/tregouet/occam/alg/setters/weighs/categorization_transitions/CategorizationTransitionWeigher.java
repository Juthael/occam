package com.tregouet.occam.alg.setters.weighs.categorization_transitions;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.weighs.Weigher;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;

public interface CategorizationTransitionWeigher extends Weigher<AProblemStateTransition> {
	
	CategorizationTransitionWeigher setContext(
			DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemGraph);

}