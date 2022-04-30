package com.tregouet.occam.alg.builders.problem_spaces.ranker.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.problem_spaces.ranker.ProblemTransitionRanker;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;

public class TopDownRanker implements ProblemTransitionRanker {
	
	private List<AProblemStateTransition> transitions;
	private int[] ranks;
	
	public TopDownRanker() {
	}

	@Override
	public void accept(AProblemStateTransition transition) {
		transition.setRank(ranks[transitions.indexOf(transition)]);
	}

	@Override
	public ProblemTransitionRanker setUp(DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemSpace) {
		int nbOfStates = problemSpace.vertexSet().size();
		transitions = new ArrayList<>(nbOfStates);
		ranks = new int[nbOfStates];
		//find start state
		IProblemState startState = null;
		Iterator<IProblemState> stateIte = problemSpace.vertexSet().iterator();
		while (startState == null) {
			IProblemState nextState = stateIte.next();
			if (problemSpace.incomingEdgesOf(nextState).size() == 0)
				startState = nextState;
		}
		//set up parameters
		setRank(problemSpace, startState, 1);
		return this;
	}
	
	private void setRank(DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemSpace, 
			IProblemState problemState, int rank) {
		for (AProblemStateTransition transition : problemSpace.outgoingEdgesOf(problemState)) {
			transitions.add(transition);
			ranks[transitions.size() - 1] = rank;
			setRank(problemSpace, problemSpace.getEdgeTarget(transition), rank + 1);
		}
			
	}
	
	

}
