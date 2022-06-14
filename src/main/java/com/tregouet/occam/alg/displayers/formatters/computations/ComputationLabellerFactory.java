package com.tregouet.occam.alg.displayers.formatters.computations;

import com.tregouet.occam.alg.displayers.formatters.computations.impl.ConjunctionNoIdentity;

public class ComputationLabellerFactory {

	public static final ComputationLabellerFactory INSTANCE = new ComputationLabellerFactory();

	private ComputationLabellerFactory() {
	}

	public ComputationLabeller apply(ComputationLabellerStrategy strategy) {
		switch (strategy) {
		case CONJUNCTION_NO_IDENTITY :
			return ConjunctionNoIdentity.INSTANCE;
		default :
			return null;
		}
	}

}
