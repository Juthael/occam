package com.tregouet.occam.alg.scorers.problem_states.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

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
