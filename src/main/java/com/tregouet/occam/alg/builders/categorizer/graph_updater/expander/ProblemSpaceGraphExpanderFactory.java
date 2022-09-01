package com.tregouet.occam.alg.builders.categorizer.graph_updater.expander;

import com.tregouet.occam.alg.builders.categorizer.graph_updater.expander.impl.AddNewStatesThenBuildTransitions;

public class ProblemSpaceGraphExpanderFactory {

	public static final ProblemSpaceGraphExpanderFactory INSTANCE = new ProblemSpaceGraphExpanderFactory();

	private ProblemSpaceGraphExpanderFactory() {
	}

	public ProblemSpaceGraphExpander apply(ProblemSpaceGraphExpanderStrategy strategy) {
		switch (strategy) {
		case ADD_NEW_STATES_THEN_BUILD_TRANSITIONS :
			return AddNewStatesThenBuildTransitions.INSTANCE;
		default :
			return null;
		}
	}

}
