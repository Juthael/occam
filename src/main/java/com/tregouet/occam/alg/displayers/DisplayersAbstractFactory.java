package com.tregouet.occam.alg.displayers;

import com.tregouet.occam.alg.displayers.properties.PropertyDisplayer;
import com.tregouet.occam.alg.displayers.properties.PropertyDisplayerFactory;
import com.tregouet.occam.alg.displayers.properties.PropertyDisplayerStrategy;
import com.tregouet.occam.alg.displayers.transition_functions.TransitionFunctionDisplayer;
import com.tregouet.occam.alg.displayers.transition_functions.TransitionFunctionDisplayerFactory;
import com.tregouet.occam.alg.displayers.transition_functions.TransitionFunctionDisplayerStrategy;

public class DisplayersAbstractFactory {
	
	public static final DisplayersAbstractFactory INSTANCE = new DisplayersAbstractFactory(); 
	
	private TransitionFunctionDisplayerStrategy transitionFunctionDisplayerStrategy = null;
	private PropertyDisplayerStrategy propertyDisplayerStrategy = null;
	
	private DisplayersAbstractFactory() {
	}
	
	public void setUpStrategy(DisplayStrategy strategy) {
		switch (strategy) {
			case DISPLAY_STRATEGY_1 : 
				transitionFunctionDisplayerStrategy = TransitionFunctionDisplayerStrategy.REMOVE_NON_SALIENT_APP;
				propertyDisplayerStrategy = PropertyDisplayerStrategy.AS_LAMBDA;
			default : 
				break;
		}
	}
	
	public TransitionFunctionDisplayer getTransitionFunctionDisplayer() {
		return TransitionFunctionDisplayerFactory.INSTANCE.apply(transitionFunctionDisplayerStrategy);
	}
	
	public PropertyDisplayer getPropertyDisplayer() {
		return PropertyDisplayerFactory.INSTANCE.apply(propertyDisplayerStrategy);
	}

}
