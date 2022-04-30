package com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.problem_spaces.IProblemState;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;

public class RankedPartitionsWeight implements ProblemTransitionWeigher {
	
	public static final RankedPartitionsWeight INSTANCE = new RankedPartitionsWeight();
	
	private RankedPartitionsWeight() {
	}

	@Override
	public void accept(AProblemStateTransition transition) {
		int transitionRank = transition.rank();
		double weight = 0.0;
		Set<AbstractDifferentiae> relevantDiff = new HashSet<>();
		for (IPartition partition : transition.getPartitions()) {
			for (AbstractDifferentiae diff : partition.getDifferentiae()) {
				if (diff.rank() == transitionRank)
					relevantDiff.add(diff);
			}
		}
		for (AbstractDifferentiae diff : relevantDiff)
			weight += diff.weight();
		transition.setWeight(weight);
	}

	@Override
	public ProblemTransitionWeigher setContext(
			DirectedAcyclicGraph<IProblemState, AProblemStateTransition> problemGraph) {
		return this;
	}

}
