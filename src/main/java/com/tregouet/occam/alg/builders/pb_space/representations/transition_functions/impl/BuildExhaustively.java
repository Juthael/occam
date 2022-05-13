package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;

public class BuildExhaustively extends AbstractTransFuncBuilder implements RepresentationTransFuncBuilder {

	@Override
	protected Set<IConceptTransition> filter(Set<IConceptTransition> transitions) {
		return transitions;
	}

}
