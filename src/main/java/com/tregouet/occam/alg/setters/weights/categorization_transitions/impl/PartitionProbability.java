package com.tregouet.occam.alg.setters.weights.categorization_transitions.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.weights.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.modules.sorting.transitions.AProblemStateTransition;
import com.tregouet.occam.data.modules.sorting.transitions.partitions.IPartition;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;

public class PartitionProbability implements ProblemTransitionWeigher {

	protected DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph;
	protected Map<AProblemStateTransition, Double> transition2Informativity;

	public PartitionProbability() {
	}

	@Override
	public void accept(AProblemStateTransition transition) {
		if (transition.weight() == null) {
			double transitionProbability;
			// always false in this impl
			boolean transitionIsMandatory = isMandatory(transition);
			IRepresentation transitionSource = problemGraph.getEdgeSource(transition);
			Set<AProblemStateTransition> concurrentTransitions = problemGraph.outgoingEdgesOf(transitionSource);
			// always 0 in this impl
			int nbOfMandatoryConcurrentTransitions = howManyTransitionsAreMandatory(concurrentTransitions);
			if (transitionIsMandatory)
				transitionProbability = 1.0 / nbOfMandatoryConcurrentTransitions;
			else {
				if (nbOfMandatoryConcurrentTransitions > 0)
					transitionProbability = 0.0;
				else {
					double summedInformativities = 0.0;
					for (AProblemStateTransition concurrentTransition : concurrentTransitions) {
						//late retrieval
						if (!transition2Informativity.containsKey(concurrentTransition)) {
							/*
							 * Then the transition has already been built as the result of a trivial transition
							 * from another source representation. This transition's weight is inaccurate and must be
							 * recalculated. A side-effect-free procedure would be preferable.
							 */
							transition2Informativity.put(concurrentTransition, informativity(concurrentTransition));
							//SIDE EFFECT
							this.accept(concurrentTransition);
						}
						summedInformativities += transition2Informativity.get(concurrentTransition);
					}
					if (summedInformativities == 0.0)
						transitionProbability = 0.0;
					else {
						double transitionInformativity = transition2Informativity.get(transition);
						transitionProbability = transitionInformativity / summedInformativities;
					}
				}
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

	protected int howManyTransitionsAreMandatory(Set<AProblemStateTransition> transitions) {
		return 0;
	}

	protected boolean isMandatory(AProblemStateTransition transition) {
		return false;
	}

	private static double informativity(AProblemStateTransition transition) {
		double informativity = 0.0;
		for (IPartition transitionPartition : transition.getPartitions()) {
			for (ADifferentiae differentia : transitionPartition.getDifferentiae())
				informativity += differentia.weight();
		}
		return informativity;
	}

}
