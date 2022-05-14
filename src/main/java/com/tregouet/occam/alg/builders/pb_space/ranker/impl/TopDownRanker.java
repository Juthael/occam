package com.tregouet.occam.alg.builders.pb_space.ranker.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.pb_space.ranker.ProblemTransitionRanker;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;

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
	public ProblemTransitionRanker setUp(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemSpace) {
		int nbOfEdges = problemSpace.edgeSet().size();
		transitions = new ArrayList<>(nbOfEdges);
		ranks = new int[nbOfEdges];
		//find start state
		IRepresentation startState = null;
		Iterator<IRepresentation> stateIte = problemSpace.vertexSet().iterator();
		while (startState == null) {
			IRepresentation nextState = stateIte.next();
			if (problemSpace.incomingEdgesOf(nextState).size() == 0)
				startState = nextState;
		}
		//set up parameters
		setRank(problemSpace, startState, 1);
		return this;
	}
	
	private void setRank(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemSpace, 
			IRepresentation problemState, int rank) {
		for (AProblemStateTransition transition : problemSpace.outgoingEdgesOf(problemState)) {
			int transIdx = transitions.indexOf(transition);
			if (transIdx == -1) {
				transitions.add(transition);
				ranks[transitions.size() - 1] = rank;
			}
			else {
				if (ranks[transIdx] < rank)
					ranks[transIdx] = rank;
			}
			setRank(problemSpace, problemSpace.getEdgeTarget(transition), rank + 1);
		}
	}
	
	

}
