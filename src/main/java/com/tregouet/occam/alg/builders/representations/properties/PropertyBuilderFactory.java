package com.tregouet.occam.alg.builders.representations.properties;

import com.tregouet.occam.alg.builders.representations.properties.impl.GroupSalientApplicationsByFunction;

public class PropertyBuilderFactory {
	
	public static final PropertyBuilderFactory INSTANCE = new PropertyBuilderFactory();
	
	private PropertyBuilderFactory() {
	}
	
	public IPropertyBuilder apply(PropertyConstructionStrategy strategy) {
		switch(strategy) {
			case GROUP_APPLICATIONS_BY_FUNCTION : 
				return new GroupSalientApplicationsByFunction();
			default : 
				return null;
		}
	}

}
