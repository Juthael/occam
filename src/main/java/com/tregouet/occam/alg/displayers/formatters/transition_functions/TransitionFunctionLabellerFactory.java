package com.tregouet.occam.alg.displayers.formatters.transition_functions;

import com.tregouet.occam.alg.displayers.formatters.transition_functions.impl.DisplayAllTransitions;

public class TransitionFunctionLabellerFactory {

	public static final TransitionFunctionLabellerFactory INSTANCE = new TransitionFunctionLabellerFactory();

	private TransitionFunctionLabellerFactory() {
	}

	public TransitionFunctionLabeller apply(TransitionFunctionLabellerStrategy strategy) {
		switch (strategy) {
		case DISPLAY_ALL_TRANSITIONS:
			return new DisplayAllTransitions();
		default:
			return null;
		}
	}

}
