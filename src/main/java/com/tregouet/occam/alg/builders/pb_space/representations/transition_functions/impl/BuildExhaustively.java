package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;

public class BuildExhaustively extends AbstractTransFuncBuilder implements RepresentationTransFuncBuilder {

	@Override
	protected void filterForComplianceToAdditionalConstraints(Set<IConceptTransition> transitions) {
		//do nothing
	}

}
