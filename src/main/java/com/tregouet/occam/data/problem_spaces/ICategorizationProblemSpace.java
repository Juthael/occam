package com.tregouet.occam.data.problem_spaces;

import org.jgrapht.graph.DirectedAcyclicGraph;

public interface ICategorizationProblemSpace {
	
	DirectedAcyclicGraph<ICategorizationState, ACategorizationStateTransition> asGraph();
	
	boolean setMaxNbOfGoalStates(int maxNbOfGoalStates);
	
	

}
