package com.tregouet.occam.alg.setters.weighs.categorization_transitions;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.weighs.Weigher;
import com.tregouet.occam.data.problem_spaces.ACategorizationTransition;
import com.tregouet.occam.data.problem_spaces.ICategorizationState;

public interface CategorizationTransitionWeigher extends Weigher<ACategorizationTransition> {
	
	CategorizationTransitionWeigher setContext(
			DirectedAcyclicGraph<ICategorizationState, ACategorizationTransition> problemGraph);

}
