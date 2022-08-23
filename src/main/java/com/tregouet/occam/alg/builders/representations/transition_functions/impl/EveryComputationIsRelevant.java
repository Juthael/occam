package com.tregouet.occam.alg.builders.representations.transition_functions.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.structures.representations.transitions.IConceptTransition;

public class EveryComputationIsRelevant extends AbstractTransFuncBuilder implements RepresentationTransFuncBuilder {

	@Override
	protected void filterForComplianceWithAdditionalConstraints(Set<IConceptTransition> transitions) {
		//do nothing
	}

	@Override
	protected Set<IComputation> selectRelevantComputations(Set<IComputation> computations) {
		return computations;
	}

}
