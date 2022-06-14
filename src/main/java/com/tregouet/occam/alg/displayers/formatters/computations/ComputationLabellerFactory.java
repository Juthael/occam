package com.tregouet.occam.alg.displayers.formatters.computations;

import com.tregouet.occam.alg.displayers.formatters.computations.impl.AngleBrackets;

public class ComputationLabellerFactory {

	public static final ComputationLabellerFactory INSTANCE = new ComputationLabellerFactory();

	private ComputationLabellerFactory() {
	}

	public ComputationLabeller apply(ComputationLabellerStrategy strategy) {
		switch (strategy) {
		case ANGLE_BRACKETS :
			return AngleBrackets.INSTANCE;
		default :
			return null;
		}
	}

}
