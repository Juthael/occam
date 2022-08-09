package com.tregouet.occam.alg.displayers.formatters.differentiae.properties;

import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.impl.CurlyBracketsWithWeight;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.impl.CurlyBracketsNoIdentity;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.impl.CurlyBracketsNoIdentityWithWeight;

public class PropertyLabellerFactory {

	public static final PropertyLabellerFactory INSTANCE = new PropertyLabellerFactory();

	private PropertyLabellerFactory() {
	}

	public PropertyLabeller apply(PropertyLabellerStrategy strategy) {
		switch (strategy) {
		case CURLY_BRACKETS_WITH_WEIGHT : 
			return CurlyBracketsWithWeight.INSTANCE;
		case CURLY_BRACKETS_NO_ID :
			return CurlyBracketsNoIdentity.INSTANCE;
		case CURLY_BRACKETS_NO_ID_WITH_WEIGHT :
			return CurlyBracketsNoIdentityWithWeight.INSTANCE;
		default:
			return null;
		}
	}

}
