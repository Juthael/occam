package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.problem_space.states.productions.IClassificationProductions;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;

public class BuildFromSalientApplications extends AbstractTransFuncBuilder implements RepresentationTransFuncBuilder {

	@Override
	protected void filterForComplianceToAdditionalConstraints(Set<IConceptTransition> transitions) {
		//do nothing
	}

	@Override
	protected Set<IContextualizedProduction> selectRelevantApplications(
			IClassificationProductions classificationProductions) {
		return classificationProductions.getSalientProductions();
	}

}
