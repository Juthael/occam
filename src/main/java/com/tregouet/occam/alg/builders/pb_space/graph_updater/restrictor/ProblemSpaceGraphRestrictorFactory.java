package com.tregouet.occam.alg.builders.pb_space.graph_updater.restrictor;

import com.tregouet.occam.alg.builders.pb_space.graph_updater.restrictor.impl.BuildNewGraph;

public class ProblemSpaceGraphRestrictorFactory {

	public static final ProblemSpaceGraphRestrictorFactory INSTANCE = new ProblemSpaceGraphRestrictorFactory();

	private ProblemSpaceGraphRestrictorFactory() {
	}

	public ProblemSpaceGraphRestrictor apply (ProblemSpaceGraphRestrictorStrategy strategy) {
		switch (strategy) {
		case BUILD_NEW_GRAPH :
			return BuildNewGraph.INSTANCE;
		default :
			return null;
		}
	}

}
