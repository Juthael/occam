package com.tregouet.occam.alg.builders.pb_space;

import com.tregouet.occam.alg.builders.pb_space.impl.NormalizeClassificationThenBuildProductions;

public class ProblemSpaceExplorerFactory {

	public static final ProblemSpaceExplorerFactory INSTANCE = new ProblemSpaceExplorerFactory();

	private ProblemSpaceExplorerFactory() {
	}

	public ProblemSpaceExplorer apply(ProblemSpaceExplorerStrategy strategy) {
		switch (strategy) {
		case NORMALIZE_CLASS_THEN_BUILD :
			return new NormalizeClassificationThenBuildProductions();
		default :
			return null;
		}
	}

}
