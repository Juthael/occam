package com.tregouet.occam.alg.scorers.property_weighing;

import com.tregouet.occam.alg.scorers.property_weighing.impl.InstantiatedVarCounter;

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
