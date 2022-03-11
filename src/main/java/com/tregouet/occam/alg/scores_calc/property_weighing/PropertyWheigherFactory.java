package com.tregouet.occam.alg.scores_calc.property_weighing;

import com.tregouet.occam.alg.scores_calc.property_weighing.impl.InstantiatedVarCounter;

public class PropertyWheigherFactory {
	
	public static final PropertyWheigherFactory INSTANCE = new PropertyWheigherFactory();
	
	private PropertyWheigherFactory() {
	}
	
	public IPropertyWheigher apply(PropertyWheighingStrategy strategy) {
		switch(strategy) {
			case NB_OF_INSTANTIATED_VAR : 
				return InstantiatedVarCounter.INSTANCE;
			default : 
				return null;
		}
	}

}
