package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;

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
