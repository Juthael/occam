package com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;

public class PartitionProbability implements ProblemTransitionWeigher {
	
	private DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph;
	private Map<AProblemStateTransition, Double> transition2Informativity;
	
	public PartitionProbability() {
	}

	@Override
	public void accept(AProblemStateTransition transition) {
		if (transition.weight() == null) {
			double transitionProbability;
			IRepresentation transitionSource = problemGraph.getEdgeSource(transition);
			Set<AProblemStateTransition> concurrentTransitions = problemGraph.outgoingEdgesOf(transitionSource);
			double summedInformativities = 0.0;
			for (AProblemStateTransition concurrentTransition : concurrentTransitions)
				summedInformativities += transition2Informativity.get(concurrentTransition);
			if (summedInformativities == 0.0)
				transitionProbability = 0.0;
			else {
				double transitionInformativity = transition2Informativity.get(transition);
				transitionProbability = transitionInformativity / summedInformativities;
			}
			transition.setWeight(transitionProbability);
		}		
	}

	@Override
	public ProblemTransitionWeigher setContext(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		this.problemGraph = problemGraph;
		transition2Informativity = new HashMap<>();
		for (AProblemStateTransition transition : problemGraph.edgeSet()) {
			if (transition.weight() == null)
				transition2Informativity.put(transition, informativity(transition));
		}
		return this;
	}
	
	private static double informativity(AProblemStateTransition transition) {
		double informativity = 0.0;
		for (IPartition transitionPartition : transition.getPartitions()) {
			for (AbstractDifferentiae differentia : transitionPartition.getDifferentiae())
				informativity += differentia.weight();
		}
		return informativity;
	}

}
