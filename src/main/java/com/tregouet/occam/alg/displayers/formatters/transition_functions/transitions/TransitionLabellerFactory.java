package com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions;

import com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.impl.CanonicalNotation;

public class TransitionLabellerFactory {
	
	public static final TransitionLabellerFactory INSTANCE = new TransitionLabellerFactory();
	
	private TransitionLabellerFactory() {
	}
	
	public TransitionLabeller apply(TransitionLabellerStrategy strategy) {
		switch (strategy) {
		case CANONICAL_NOTATION : 
			return CanonicalNotation.INSTANCE;
		default : 
			return null;
		}
	}

}
