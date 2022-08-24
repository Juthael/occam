package com.tregouet.occam.alg.scorers.categorizations.impl;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.scorers.categorizations.ProblemStateScorer;
import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.structures.representations.IRepresentation;

public class SourcesProbabilitiesTimesTransitionsProbabilities implements ProblemStateScorer {

	List<IRepresentation> topoOrder;
	double[] scores;

	@Override
	public double score(IRepresentation representation) {
		return scores[topoOrder.indexOf(representation)];
	}

	@Override
	public ProblemStateScorer setUp(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemSpace) {
		topoOrder = new ArrayList<>();
		new TopologicalOrderIterator<>(problemSpace).forEachRemaining(topoOrder::add);
		scores = new double[topoOrder.size()];
		scores[0] = 1.0;
		for (int i = 1 ; i < topoOrder.size() ; i++) {
			IRepresentation current = topoOrder.get(i);
			for (AProblemStateTransition incomingTransition : problemSpace.incomingEdgesOf(current)) {
				IRepresentation transitionSource = problemSpace.getEdgeSource(incomingTransition);
				int transitionSourceIdx = topoOrder.indexOf(transitionSource);
				double sourceProbability = scores[transitionSourceIdx];
				double transitionProbability = incomingTransition.weight();
				scores[i] += sourceProbability * transitionProbability;
			}
		}
		return this;
	}

}
