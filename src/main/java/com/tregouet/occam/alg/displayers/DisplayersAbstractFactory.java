package com.tregouet.occam.alg.displayers;

import com.tregouet.occam.alg.displayers.transition_function.TransitionFunctionDisplayer;
import com.tregouet.occam.alg.displayers.transition_function.TransitionFunctionDisplayerFactory;
import com.tregouet.occam.alg.displayers.transition_function.TransitionFunctionDisplayerStrategy;

public class DisplayersAbstractFactory {
	
	public static final DisplayersAbstractFactory INSTANCE = new DisplayersAbstractFactory(); 
	
	private TransitionFunctionDisplayerStrategy transitionFunctionDisplayerStrategy = null;
	
	private DisplayersAbstractFactory() {
	}
	
	public void setUpStrategy(DisplayStrategy strategy) {
		switch (strategy) {
			case DISPLAY_STRATEGY_1 : 
				transitionFunctionDisplayerStrategy = TransitionFunctionDisplayerStrategy.REMOVE_NON_SALIENT_APP;
			default : 
				break;
		}
	}
	
	public TransitionFunctionDisplayer getTransitionFunctionDisplayer() {
		return TransitionFunctionDisplayerFactory.INSTANCE.apply(transitionFunctionDisplayerStrategy);
	}

}
