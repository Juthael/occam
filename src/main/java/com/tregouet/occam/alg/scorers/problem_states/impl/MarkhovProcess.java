package com.tregouet.occam.alg.scorers.problem_states.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.data.logical_structures.orders.total.impl.DoubleScore;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.tree_finder.utils.Functions;

public class MarkhovProcess implements ProblemStateScorer {

	private DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemSpace = null;
	private List<IRepresentation> topoOrderedStates = new ArrayList<>();
	private double[] stateProbability = null;

	public MarkhovProcess() {
	}

	@Override
	public DoubleScore apply(IRepresentation problemState) {
		return new DoubleScore(stateProbability[topoOrderedStates.indexOf(problemState)]);
	}

	@Override
	public ProblemStateScorer setUp(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemSpace) {
		this.problemSpace = problemSpace;
		new TopologicalOrderIterator<>(problemSpace).forEachRemaining(topoOrderedStates::add);
		stateProbability = new double[topoOrderedStates.size()];
		Arrays.fill(stateProbability, 1.0);
		for (IRepresentation problemState : problemSpace)
			updateSuccessors(problemState);
		return this;
	}

	private void updateSuccessors(IRepresentation source) {
		Set<AProblemStateTransition> outgoingTransitions = problemSpace.outgoingEdgesOf(source);
		double outgoingTransitionWeightSum = 0.0;
		for (AProblemStateTransition transition : outgoingTransitions)
			outgoingTransitionWeightSum += transition.weight();
		for (AProblemStateTransition transition : outgoingTransitions) {
			double transitionProbability = transition.weight() / outgoingTransitionWeightSum;
			IRepresentation successor = transition.getTarget();
			Set<IRepresentation> upperSet = Functions.upperSet(problemSpace, successor);
			for (IRepresentation upperBound : upperSet) {
				stateProbability[topoOrderedStates.indexOf(upperBound)] *= transitionProbability;
			}
		}
	}

}
