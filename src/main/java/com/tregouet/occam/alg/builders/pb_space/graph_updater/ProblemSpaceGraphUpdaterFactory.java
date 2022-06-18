package com.tregouet.occam.alg.builders.pb_space.graph_updater;

import com.tregouet.occam.alg.builders.pb_space.graph_updater.impl.BuildNewTransitions;

public class ProblemSpaceGraphUpdaterFactory {
	
	public static final ProblemSpaceGraphUpdaterFactory INSTANCE = new ProblemSpaceGraphUpdaterFactory();
	
	private ProblemSpaceGraphUpdaterFactory() {
	}
	
	public ProblemSpaceGraphUpdater apply(ProblemSpaceGraphUpdaterStrategy strategy) {
		switch (strategy) {
		case BUILD_NEW_TRANSITIONS : 
			return BuildNewTransitions.INSTANCE;
		default : 
			return null;
		}
	}

}
