package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.problem_space.states.productions.IClassificationProductions;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.Salience;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;

public class FromBlankAndSalientProductions extends AbstractTransFuncBuilder implements RepresentationTransFuncBuilder {

	@Override
	protected void filterForComplianceToAdditionalConstraints(Set<IConceptTransition> transitions) {
		//do nothing
	}

	@Override
	protected Set<IContextualizedProduction> selectRelevantProductions(
			IClassificationProductions classificationProductions) {
		Set<IContextualizedProduction> relevantProductions = new HashSet<>();
		for (IContextualizedProduction production : classificationProductions.getProductions()) {
			if (!production.isEpsilon()) {
				if (production.isBlank())
					relevantProductions.add(production);
				else {
					Salience salience = classificationProductions.salienceOf(production);
					if (salience == Salience.COMMON_FEATURE || salience == Salience.TRANSITION_RULE)
						relevantProductions.add(production);
				}
			}
		}
		return relevantProductions;
	}

}
