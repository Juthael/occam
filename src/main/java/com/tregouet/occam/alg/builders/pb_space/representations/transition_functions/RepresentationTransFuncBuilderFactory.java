package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions;

import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.impl.FromBlankAndSalientProductions;

public class RepresentationTransFuncBuilderFactory {

	public static final RepresentationTransFuncBuilderFactory INSTANCE = new RepresentationTransFuncBuilderFactory();

	private RepresentationTransFuncBuilderFactory() {
	}

	public RepresentationTransFuncBuilder apply(RepresentationTransFuncBuilderStrategy strategy) {
		switch (strategy) {
		case FROM_BLANK_AND_SALIENT_PRODS:
			return new FromBlankAndSalientProductions();
		default:
			return null;
		}
	}

}
