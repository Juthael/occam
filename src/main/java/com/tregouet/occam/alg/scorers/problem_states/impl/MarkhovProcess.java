package com.tregouet.occam.alg.scorers.problem_states.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.data.logical_structures.orders.total.impl.LecticScore;
import com.tregouet.occam.data.logical_structures.orders.total.impl.Size1LecticScore;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;

public class MarkhovProcess implements ProblemStateScorer {

	private DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemSpace = null;
	private List<IProblemState> topoOrderedStates = new ArrayList<>();
	private double[] stateProbability = null;

	public MarkhovProcess() {
	}

	@Override
	public LecticScore apply(IProblemState problemState) {
		return new Size1LecticScore(
				stateProbability[topoOrderedStates.indexOf(problemState)]);
	}

	private void incrementTargetScores(IProblemState source) {
		Set<AProblemStateTransition> outgoingTransitions = problemSpace.outgoingEdgesOf(source);
		double outgoingTransitionWeightSum = 0.0;
		for (AProblemStateTransition transition : outgoingTransitions)
			outgoingTransitionWeightSum += transition.weight();
		for (AProblemStateTransition transition : outgoingTransitions) {
			double transitionProbability = transition.weight() / outgoingTransitionWeightSum;
			stateProbability[topoOrderedStates.indexOf(transition.getTarget())] += transitionProbability;
		}
	}

	@Override
	public ProblemStateScorer setUp(
			DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemSpace) {
		this.problemSpace = problemSpace;
		new TopologicalOrderIterator<>(problemSpace)
			.forEachRemaining(topoOrderedStates::add);
		stateProbability = new double[topoOrderedStates.size()];
		for (IProblemState problemState : problemSpace)
			incrementTargetScores(problemState);
		//start state
		stateProbability[0] = 1;
		return this;
	}

}
