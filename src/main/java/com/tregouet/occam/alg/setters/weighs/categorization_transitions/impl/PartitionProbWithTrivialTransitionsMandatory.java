package com.tregouet.occam.alg.setters.weighs.categorization_transitions.impl;

import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.modules.categorization.transitions.partitions.IPartition;

public class PartitionProbWithTrivialTransitionsMandatory extends PartitionProbability
		implements ProblemTransitionWeigher {

	@Override
	protected int howManyTransitionsAreMandatory(Set<AProblemStateTransition> transitions) {
		return howManyTransitionsAreTrivialSorting(transitions);
	}

	@Override
	protected boolean isMandatory(AProblemStateTransition transition) {
		return isTrivialSortingOfUniversalWithCardinal2(transition);
	}

	private int howManyTransitionsAreTrivialSorting(Set<AProblemStateTransition> transitions) {
		int count = 0;
		for (AProblemStateTransition transition : transitions) {
			if (isTrivialSortingOfUniversalWithCardinal2(transition))
				count++;
		}
		return count;
	}

	private boolean isTrivialSortingOfUniversalWithCardinal2(AProblemStateTransition transition) {
		for (IPartition partition : transition.getPartitions()) {
			Integer[] speciesIDs = partition.getSpeciesIDs();
			if (speciesIDs.length != 2)
				return false;
			for (Integer speciesID : speciesIDs) {
				List<Integer> speciesExtent = partition.getLeaf2ExtentMap().get(speciesID);
				if (speciesExtent.size() != 1)
					return false;
			}
		}
		return true;
	}

}
