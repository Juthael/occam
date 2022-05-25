package com.tregouet.occam.alg.builders.pb_space.representations;

import com.tregouet.occam.alg.builders.pb_space.representations.impl.FirstBuildTransitionFunction;

public class RepresentationBuilderFactory {
	
	public static final RepresentationBuilderFactory INSTANCE = new RepresentationBuilderFactory();
	
	private RepresentationBuilderFactory() {
	}
	
	public RepresentationBuilder apply(RepresentationBuilderStrategy strategy) {
		switch (strategy) {
		case FIRST_BUILD_TRANSITION_FUNC : 
			return new FirstBuildTransitionFunction();
		default : 
			return null;
		}
	}

}
