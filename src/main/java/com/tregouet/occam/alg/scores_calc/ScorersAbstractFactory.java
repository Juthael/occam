package com.tregouet.occam.alg.scores_calc;

import com.tregouet.occam.alg.scores_calc.property_weighing.IPropertyWheigher;
import com.tregouet.occam.alg.scores_calc.property_weighing.PropertyWheigherFactory;
import com.tregouet.occam.alg.scores_calc.property_weighing.PropertyWheighingStrategy;

public class ScorersAbstractFactory {
	
	public static final ScorersAbstractFactory INSTANCE = new ScorersAbstractFactory();
	
	private PropertyWheighingStrategy propertyWheighingStrategy = null;
	
	private ScorersAbstractFactory() {
	}
	
	public IPropertyWheigher getPropertyWheigher() {
		return PropertyWheigherFactory.INSTANCE.apply(propertyWheighingStrategy);
	}
	
	public void setUpStrategy(ScoringStrategy overallStrategy) {
		switch (overallStrategy) {
			case SCORING_STRATEGY_1 : 
				propertyWheighingStrategy = PropertyWheighingStrategy.NB_OF_INSTANTIATED_VAR;
				break;
			default : 
				break;
		}
	}

}
