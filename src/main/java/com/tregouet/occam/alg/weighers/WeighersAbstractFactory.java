package com.tregouet.occam.alg.weighers;

import com.tregouet.occam.alg.weighers.properties.PropertyWeigher;
import com.tregouet.occam.alg.weighers.properties.PropertyWeigherFactory;
import com.tregouet.occam.alg.weighers.properties.PropertyWeighingStrategy;

public class WeighersAbstractFactory {
	
	public static final WeighersAbstractFactory INSTANCE = new WeighersAbstractFactory();
	
	private PropertyWeighingStrategy propertyWeighingStrategy = null;
	
	private WeighersAbstractFactory() {
	}
	
	public PropertyWeigher getPropertyWheigher() {
		return PropertyWeigherFactory.INSTANCE.apply(propertyWeighingStrategy);
	}
	
	public void setUpStrategy(WeighingStrategy overallStrategy) {
		switch(overallStrategy) {
			case SCORING_STRATEGY_1 : 
				propertyWeighingStrategy = PropertyWeighingStrategy.NB_OF_INSTANTIATED_VAR;
				break;
			default : 
				break;
		}
	}

}
