package com.tregouet.occam.alg.builders.representations;

import com.tregouet.occam.alg.builders.representations.impl.FirstBuildTransitionFunction;

public class RepresentationBuilderFactory {
	
	public static final RepresentationBuilderFactory INSTANCE = new RepresentationBuilderFactory();
	
	private RepresentationBuilderFactory() {
	}
	
	public RepresentationBuilder apply(RepresentationBuilderStrategy strategy) {
		switch (strategy) {
		case FIRST_BUILD_TRANSITION_FUNC : 
			return FirstBuildTransitionFunction.INSTANCE;
		default : 
			return null;
		}
	}

}
