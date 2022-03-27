package com.tregouet.occam.alg.setters.weighs.properties;

import com.tregouet.occam.alg.setters.weighs.properties.impl.InstantiatedVarCounter;

public class PropertyWeigherFactory {
	
	public static final PropertyWeigherFactory INSTANCE = new PropertyWeigherFactory();
	
	private PropertyWeigherFactory() {
	}
	
	public PropertyWeigher apply(PropertyWeigherStrategy strategy) {
		switch(strategy) {
			case NB_OF_INSTANTIATED_VAR : 
				return InstantiatedVarCounter.INSTANCE;
			default : 
				return null;
		}
	}

}
