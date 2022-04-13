package com.tregouet.occam.alg.displayers.transition_functions;

import com.tregouet.occam.alg.displayers.transition_functions.impl.DisplayAllTransitions;
import com.tregouet.occam.alg.displayers.transition_functions.impl.RemoveNonSalientApp;
import com.tregouet.occam.alg.displayers.transition_functions.impl.RetainSalientApp;

public class TransitionFunctionDisplayerFactory {
	
	public static final TransitionFunctionDisplayerFactory INSTANCE =
			new TransitionFunctionDisplayerFactory();
	
	private TransitionFunctionDisplayerFactory() {
	}
	
	public TransitionFunctionDisplayer apply(TransitionFunctionDisplayerStrategy strategy) {
		switch (strategy) {
			case DISPLAY_ALL_TRANSITIONS : 
				return new DisplayAllTransitions();
			case REMOVE_NON_SALIENT_APP : 
				return new RemoveNonSalientApp();
			case RETAIN_SALIENT_APP : 
				return new RetainSalientApp();
			default : 
				return null;
		}
	}

}
