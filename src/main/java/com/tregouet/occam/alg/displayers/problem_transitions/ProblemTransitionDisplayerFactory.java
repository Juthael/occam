package com.tregouet.occam.alg.displayers.problem_transitions;

import com.tregouet.occam.alg.displayers.problem_transitions.impl.WeightOnly;

public class ProblemTransitionDisplayerFactory {
	
	public static final ProblemTransitionDisplayerFactory INSTANCE = 
			new ProblemTransitionDisplayerFactory();
	
	private ProblemTransitionDisplayerFactory() {
	}
	
	public ProblemTransitionDisplayer apply(ProblemTransitionDisplayerStrategy strategy) {
		switch (strategy) {
			case WEIGHT_ONLY : 
				return WeightOnly.INSTANCE;
			default : 
				return null;
		}
	}

}
