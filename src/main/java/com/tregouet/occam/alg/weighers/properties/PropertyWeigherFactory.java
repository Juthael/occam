package com.tregouet.occam.alg.weighers.properties;

import com.tregouet.occam.alg.weighers.properties.impl.InstantiatedVarCounter;

public class PropertyWeigherFactory {
	
	public static final PropertyWeigherFactory INSTANCE = new PropertyWeigherFactory();
	
	private PropertyWeigherFactory() {
	}
	
	public IPropertyWeigher apply(PropertyWeighingStrategy strategy) {
		switch(strategy) {
			case NB_OF_INSTANTIATED_VAR : 
				return InstantiatedVarCounter.INSTANCE;
			default : 
				return null;
		}
	}

}
