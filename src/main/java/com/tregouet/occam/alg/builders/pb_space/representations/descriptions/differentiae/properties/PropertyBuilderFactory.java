package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties;

import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.impl.GroupSalientProductionsByApplication;

public class PropertyBuilderFactory {

	public static final PropertyBuilderFactory INSTANCE = new PropertyBuilderFactory();

	private PropertyBuilderFactory() {
	}

	public PropertyBuilder apply(PropertyBuilderStrategy strategy) {
		switch (strategy) {
		case GROUP_PRODUCTIONS_BY_APPLICATION:
			return new GroupSalientProductionsByApplication();
		default:
			return null;
		}
	}

}
