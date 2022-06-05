package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions;

import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.impl.FromBlankAndSalientProductions;

public class RepresentationTransFuncBuilderFactory {

	public static final RepresentationTransFuncBuilderFactory INSTANCE = new RepresentationTransFuncBuilderFactory();

	private RepresentationTransFuncBuilderFactory() {
	}

	public RepresentationTransFuncBuilder apply(RepresentationTransFuncBuilderStrategy strategy) {
		switch (strategy) {
		case BUILD_FROM_SALIENT_APPLICATIONS:
			return new FromBlankAndSalientProductions();
		default:
			return null;
		}
	}

}
