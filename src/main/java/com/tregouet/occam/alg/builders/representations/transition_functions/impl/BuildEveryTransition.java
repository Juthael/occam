package com.tregouet.occam.alg.builders.representations.transition_functions.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;

public class BuildEveryTransition extends AbstractTransFuncBuilder implements RepresentationTransFuncBuilder {

	@Override
	protected Set<IConceptTransition> filter(Set<IConceptTransition> transitions) {
		return transitions;
	}

}
