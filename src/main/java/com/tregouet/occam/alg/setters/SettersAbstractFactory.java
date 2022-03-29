package com.tregouet.occam.alg.setters;

import com.tregouet.occam.alg.setters.parameters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.alg.setters.parameters.differentiae_coeff.DifferentiaeCoeffSetterFactory;
import com.tregouet.occam.alg.setters.parameters.differentiae_coeff.DifferentiaeCoeffSetterStrategy;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigherFactory;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigherStrategy;
import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigherFactory;
import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigherStrategy;

public class SettersAbstractFactory {
	
	public static final SettersAbstractFactory INSTANCE = new SettersAbstractFactory();
	
	private PropertyWeigherStrategy propertyWeigherStrategy = null;
	private DifferentiaeCoeffSetterStrategy differentiaeCoeffSetterStrategy = null;
	private DifferentiaeWeigherStrategy differentiaeWeigherStrategy = null;
	
	private SettersAbstractFactory() {
	}
	
	public void setUpStrategy(SettingStrategy overallStrategy) {
		switch(overallStrategy) {
			case SCORING_STRATEGY_1 : 
				propertyWeigherStrategy = PropertyWeigherStrategy.NB_OF_INSTANTIATED_VAR;
				differentiaeCoeffSetterStrategy = DifferentiaeCoeffSetterStrategy.NO_COEFF;
				differentiaeWeigherStrategy = DifferentiaeWeigherStrategy.SUM_OF_PROPERTY_WEIGHTS;
				break;
			default : 
				break;
		}
	}
	
	public PropertyWeigher getPropertyWheigher() {
		return PropertyWeigherFactory.INSTANCE.apply(propertyWeigherStrategy);
	}
	
	public DifferentiaeCoeffSetter getDifferentiaeCoeffSetter() {
		return DifferentiaeCoeffSetterFactory.INSTANCE.apply(differentiaeCoeffSetterStrategy);
	}
	
	public DifferentiaeWeigher getDifferentiaeWeigher() {
		return DifferentiaeWeigherFactory.INSTANCE.apply(differentiaeWeigherStrategy);
	}

}