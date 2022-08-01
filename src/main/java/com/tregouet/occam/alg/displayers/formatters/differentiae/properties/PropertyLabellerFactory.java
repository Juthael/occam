package com.tregouet.occam.alg.displayers.formatters.differentiae.properties;

import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.impl.CurlyBrackets;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.impl.CurlyBracketsWithWeight;

public class PropertyLabellerFactory {

	public static final PropertyLabellerFactory INSTANCE = new PropertyLabellerFactory();

	private PropertyLabellerFactory() {
	}

	public PropertyLabeller apply(PropertyLabellerStrategy strategy) {
		switch (strategy) {
		case CURLY_BRACKETS :
			return CurlyBrackets.INSTANCE;
		case CURLY_BRACKETS_WITH_WEIGHT :
			return CurlyBracketsWithWeight.INSTANCE;
		default:
			return null;
		}
	}

}
