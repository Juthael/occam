package com.tregouet.occam.alg.displayers.graph_labellers.problem_transitions;

import com.tregouet.occam.alg.displayers.graph_labellers.problem_transitions.impl.WeightOnly;

public class ProblemTransitionLabellerFactory {
	
	public static final ProblemTransitionLabellerFactory INSTANCE = 
			new ProblemTransitionLabellerFactory();
	
	private ProblemTransitionLabellerFactory() {
	}
	
	public ProblemTransitionLabeller apply(ProblemTransitionLabellerStrategy strategy) {
		switch (strategy) {
			case WEIGHT_ONLY : 
				return WeightOnly.INSTANCE;
			default : 
				return null;
		}
	}

}
