package com.tregouet.occam.alg.displayers.formatters.differentiae.properties;

import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.impl.CurlyBrackets;

public class PropertyLabellerFactory {

	public static final PropertyLabellerFactory INSTANCE = new PropertyLabellerFactory();

	private PropertyLabellerFactory() {
	}

	public PropertyLabeller apply(PropertyLabellerStrategy strategy) {
		switch (strategy) {
		case CURLY_BRACKETS : 
			return CurlyBrackets.INSTANCE;
		default:
			return null;
		}
	}

}
