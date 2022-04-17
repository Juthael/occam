package com.tregouet.occam.alg.displayers.graph_labellers.transition_functions;

import com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.impl.DisplayAllTransitions;
import com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.impl.RemoveNonSalientApp;
import com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.impl.RetainSalientApp;

public class TransitionFunctionLabellerFactory {
	
	public static final TransitionFunctionLabellerFactory INSTANCE =
			new TransitionFunctionLabellerFactory();
	
	private TransitionFunctionLabellerFactory() {
	}
	
	public TransitionFunctionLabeller apply(TransitionFunctionLabellerStrategy strategy) {
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
