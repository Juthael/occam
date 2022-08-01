package com.tregouet.occam.alg.builders.pb_space;

import com.tregouet.occam.alg.builders.pb_space.impl.DiscardUninformativeStates;

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
