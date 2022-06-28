package com.tregouet.occam.alg.builders.pb_space;

import com.tregouet.occam.alg.builders.pb_space.impl.DevelopTrivialDiscardUninformativeStates;

public class ProblemSpaceExplorerFactory {

	public static final ProblemSpaceExplorerFactory INSTANCE = new ProblemSpaceExplorerFactory();

	private ProblemSpaceExplorerFactory() {
	}

	public ProblemSpaceExplorer apply(ProblemSpaceExplorerStrategy strategy) {
		switch (strategy) {
		case DEVELOP_TRIVIAL_DISCARD_UNINFORMATIVE :
			return new DevelopTrivialDiscardUninformativeStates();
		default :
			return null;
		}
	}

}
