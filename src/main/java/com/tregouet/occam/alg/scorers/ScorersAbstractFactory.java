package com.tregouet.occam.alg.scorers;

import com.tregouet.occam.alg.scorers.property_weighing.IPropertyWeigher;
import com.tregouet.occam.alg.scorers.property_weighing.PropertyWeigherFactory;
import com.tregouet.occam.alg.scorers.property_weighing.PropertyWeighingStrategy;

public class ScorersAbstractFactory {
	
	public static final ScorersAbstractFactory INSTANCE = new ScorersAbstractFactory();
	
	private PropertyWeighingStrategy propertyWeighingStrategy = null;
	
	private ScorersAbstractFactory() {
	}
	
	public IPropertyWeigher getPropertyWheigher() {
		return PropertyWeigherFactory.INSTANCE.apply(propertyWeighingStrategy);
	}
	
	public void setUpStrategy(ScoringStrategy overallStrategy) {
		switch(overallStrategy) {
			case SCORING_STRATEGY_1 : 
				propertyWeighingStrategy = PropertyWeighingStrategy.NB_OF_INSTANTIATED_VAR;
				break;
			default : 
				break;
		}
	}

}
