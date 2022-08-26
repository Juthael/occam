package com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.exhaustive;

import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.exhaustive.impl.PropertiesThenWeight;

public class DifferentiaeExhaustiveLabellerFactory {

	public static final DifferentiaeExhaustiveLabellerFactory INSTANCE = new DifferentiaeExhaustiveLabellerFactory();

	private DifferentiaeExhaustiveLabellerFactory() {
	}

	public DifferentiaeExhaustiveLabeller apply(DifferentiaeExhaustiveLabellerStrategy strategy) {
		switch (strategy) {
		case PROPERTIES_THEN_WEIGHT :
			return PropertiesThenWeight.INSTANCE;
		default:
			return null;
		}
	}

}
