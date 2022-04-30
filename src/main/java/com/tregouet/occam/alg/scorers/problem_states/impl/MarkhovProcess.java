package com.tregouet.occam.alg.scorers.problem_states.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.data.logical_structures.orders.total.impl.LecticScore;
import com.tregouet.occam.data.logical_structures.orders.total.impl.Size1LecticScore;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.tree_finder.utils.Functions;

public class MarkhovProcess implements ProblemStateScorer {

	private DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemSpace = null;
	private List<IProblemState> topoOrderedStates = new ArrayList<>();
	private double[] stateProbability = null;

	public MarkhovProcess() {
	}

	@Override
	public LecticScore apply(IProblemState problemState) {
		return new Size1LecticScore(stateProbability[topoOrderedStates.indexOf(problemState)]);
	}

	@Override
	public ProblemStateScorer setUp(DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemSpace) {
		this.problemSpace = problemSpace;
		new TopologicalOrderIterator<>(problemSpace).forEachRemaining(topoOrderedStates::add);
		stateProbability = new double[topoOrderedStates.size()];
		Arrays.fill(stateProbability, 1.0);
		for (IProblemState problemState : problemSpace)
			updateSuccessors(problemState);
		return this;
	}

	private void updateSuccessors(IProblemState source) {
		Set<AProblemStateTransition> outgoingTransitions = problemSpace.outgoingEdgesOf(source);
		double outgoingTransitionWeightSum = 0.0;
		for (AProblemStateTransition transition : outgoingTransitions)
			outgoingTransitionWeightSum += transition.weight();
		for (AProblemStateTransition transition : outgoingTransitions) {
			double transitionProbability = transition.weight() / outgoingTransitionWeightSum;
			IProblemState successor = transition.getTarget();
			Set<IProblemState> upperSet = Functions.upperSet(problemSpace, successor);
			for (IProblemState upperBound : upperSet) {
				stateProbability[topoOrderedStates.indexOf(upperBound)] *= transitionProbability;
			}
		}
	}

}
