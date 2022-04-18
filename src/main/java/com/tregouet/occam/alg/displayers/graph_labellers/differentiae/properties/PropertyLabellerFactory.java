package com.tregouet.occam.alg.displayers.graph_labellers.differentiae.properties;

import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.properties.impl.AsLambdaShorterString;
import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.properties.impl.AsLambdaString;
import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.properties.impl.AsProductionString;

public class PropertyLabellerFactory {

	public static final PropertyLabellerFactory INSTANCE = new PropertyLabellerFactory();

	private PropertyLabellerFactory() {
	}

	public PropertyLabeller apply(PropertyLabellerStrategy strategy) {
		switch (strategy) {
		case AS_LAMBDA:
			return AsLambdaString.INSTANCE;
		case AS_SHORTER_LAMBDA:
			return AsLambdaShorterString.INSTANCE;
		case AS_PRODUCTIONS:
			return AsProductionString.INSTANCE;
		default:
			return null;
		}
	}

}
