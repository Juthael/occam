package com.tregouet.occam.alg.displayers.graph_labellers.differentiae;

import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.impl.PropertiesThenWeight;

public class DifferentiaeLabellerFactory {

	public static final DifferentiaeLabellerFactory INSTANCE = new DifferentiaeLabellerFactory();

	private DifferentiaeLabellerFactory() {
	}

	public DifferentiaeLabeller apply(DifferentiaeLabellerStrategy strategy) {
		switch (strategy) {
		case PROPERTIES_THEN_WEIGHT:
			return PropertiesThenWeight.INSTANCE;
		default:
			return null;
		}
	}

}
