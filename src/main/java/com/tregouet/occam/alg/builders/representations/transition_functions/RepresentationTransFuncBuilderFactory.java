package com.tregouet.occam.alg.builders.representations.transition_functions;

import com.tregouet.occam.alg.builders.representations.transition_functions.impl.EveryComputationIsRelevant;

public class RepresentationTransFuncBuilderFactory {

	public static final RepresentationTransFuncBuilderFactory INSTANCE = new RepresentationTransFuncBuilderFactory();

	private RepresentationTransFuncBuilderFactory() {
	}

	public RepresentationTransFuncBuilder apply(RepresentationTransFuncBuilderStrategy strategy) {
		switch (strategy) {
		case EVERY_APP_IS_RELEVANT:
			return new EveryComputationIsRelevant();
		default:
			return null;
		}
	}

}
