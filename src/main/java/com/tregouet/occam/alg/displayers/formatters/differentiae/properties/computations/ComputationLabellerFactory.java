package com.tregouet.occam.alg.displayers.formatters.differentiae.properties.computations;

import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.computations.impl.Conjunction;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.computations.impl.ConjunctionNoIdentity;

public class ComputationLabellerFactory {

	public static final ComputationLabellerFactory INSTANCE = new ComputationLabellerFactory();

	private ComputationLabellerFactory() {
	}

	public ComputationLabeller apply(ComputationLabellerStrategy strategy) {
		switch (strategy) {
		case CONJUNCTION :
			return Conjunction.INSTANCE;
		case CONJUNCTION_NO_IDENTITY :
			return ConjunctionNoIdentity.INSTANCE;
		default :
			return null;
		}
	}

}
