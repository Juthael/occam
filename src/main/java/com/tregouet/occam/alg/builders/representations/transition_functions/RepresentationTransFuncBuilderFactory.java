package com.tregouet.occam.alg.builders.representations.transition_functions;

import com.tregouet.occam.alg.builders.representations.transition_functions.impl.BuildExhaustively;

public class RepresentationTransFuncBuilderFactory {

	public static final RepresentationTransFuncBuilderFactory INSTANCE = new RepresentationTransFuncBuilderFactory();

	private RepresentationTransFuncBuilderFactory() {
	}

	public RepresentationTransFuncBuilder apply(RepresentationTransFuncBuilderStrategy strategy) {
		switch (strategy) {
		case BUILD_EXHAUSTIVELY:
			return new BuildExhaustively();
		default:
			return null;
		}
	}

}
