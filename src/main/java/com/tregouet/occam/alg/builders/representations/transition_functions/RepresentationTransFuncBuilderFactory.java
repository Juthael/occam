package com.tregouet.occam.alg.builders.representations.transition_functions;

import com.tregouet.occam.alg.builders.representations.transition_functions.impl.BuildEveryTransition;
import com.tregouet.occam.alg.builders.representations.transition_functions.impl.RemoveNonSalientApplications;
import com.tregouet.occam.alg.builders.representations.transition_functions.impl.RetainSalientApplications;

public class RepresentationTransFuncBuilderFactory {
	
	public static final RepresentationTransFuncBuilderFactory INSTANCE = new RepresentationTransFuncBuilderFactory();
	
	private RepresentationTransFuncBuilderFactory() {
	}
	
	public RepresentationTransFuncBuilder apply(RepresentationTransFuncBuilderStrategy strategy) {
		switch(strategy) {
			case EVERY_TRANSITION :
				return new BuildEveryTransition();
			case REMOVE_NON_SALIENT_APP : 
				return new RemoveNonSalientApplications();
			case RETAIN_SALIENT_APP : 
				return new RetainSalientApplications();
			default :
				return null;
		}
	}

}
