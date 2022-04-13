package com.tregouet.occam.alg.displayers.problem_states;

import com.tregouet.occam.alg.displayers.problem_states.impl.AsNestedFrames;

public class ProblemStateDisplayerFactory {
	
	public static final ProblemStateDisplayerFactory INSTANCE = new ProblemStateDisplayerFactory();
	
	private ProblemStateDisplayerFactory() {
	}
	
	public ProblemStateDisplayer apply(ProblemStateDisplayerStrategy strategy) {
		switch(strategy) {
			case NESTED_FRAMES : 
				return AsNestedFrames.INSTANCE;
			default : 
				return null;
		}
	}

}
