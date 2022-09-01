package com.tregouet.occam.alg.scorers.categorizations.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.scorers.categorizations.ProblemStateScorer;
import com.tregouet.occam.data.modules.sorting.transitions.AProblemStateTransition;
import com.tregouet.occam.data.structures.representations.IRepresentation;

public class MarkhovProcess implements ProblemStateScorer {

	private DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemSpace = null;
	private List<IRepresentation> topoOrderedStates = new ArrayList<>();
	private double[] stateProbability = null;

	public MarkhovProcess() {
	}

	@Override
	public double score(IRepresentation problemState) {
		return stateProbability[topoOrderedStates.indexOf(problemState)];
	}

	@Override
	public ProblemStateScorer setUp(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemSpace) {
		this.problemSpace = problemSpace;
		new TopologicalOrderIterator<>(problemSpace).forEachRemaining(topoOrderedStates::add);
		stateProbability = new double[topoOrderedStates.size()];
		stateProbability[0] = 1.0;
		for (IRepresentation problemState : topoOrderedStates)
			updateSuccessors(problemState);
		return this;
	}

	private void updateSuccessors(IRepresentation source) {
		double sourceProbability = stateProbability[topoOrderedStates.indexOf(source)];
		Set<AProblemStateTransition> outgoingTransitions = problemSpace.outgoingEdgesOf(source);
		double outgoingTransitionWeightSum = 0.0;
		for (AProblemStateTransition transition : outgoingTransitions)
			outgoingTransitionWeightSum += transition.weight();
		for (AProblemStateTransition transition : outgoingTransitions) {
			double transitionProbability;
			if (outgoingTransitionWeightSum == 0)
				transitionProbability = 0;
			else transitionProbability = transition.weight() / outgoingTransitionWeightSum;
			IRepresentation successor = transition.getTarget();
			int succIdx = topoOrderedStates.indexOf(successor);
			stateProbability[succIdx] += sourceProbability * transitionProbability;
		}
	}

}
