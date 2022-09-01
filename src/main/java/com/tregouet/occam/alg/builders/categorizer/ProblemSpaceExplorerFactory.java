package com.tregouet.occam.alg.builders.categorizer;

import com.tregouet.occam.alg.builders.categorizer.impl.DiscardUninformativeStates;

public class ProblemSpaceExplorerFactory {

	public static final ProblemSpaceExplorerFactory INSTANCE = new ProblemSpaceExplorerFactory();

	private ProblemSpaceExplorerFactory() {
	}

	public ProblemSpaceExplorer apply(ProblemSpaceExplorerStrategy strategy) {
		switch (strategy) {
		case DISCARD_UNINFORMATIVE_STATES :
			return new DiscardUninformativeStates();
		default :
			return null;
		}
	}

}
