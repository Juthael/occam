package com.tregouet.occam.alg.builders.representations.transition_functions;

import com.tregouet.occam.alg.builders.representations.transition_functions.impl.AbstractFactsAccepted;

public class RepresentationTransFuncBuilderFactory {
	
	public static final RepresentationTransFuncBuilderFactory INSTANCE = new RepresentationTransFuncBuilderFactory();
	
	private RepresentationTransFuncBuilderFactory() {
	}
	
	public IRepresentationTransFuncBuilder apply(RepresentationTransFuncConstructionStrategy strategy) {
		switch(strategy) {
			case ABSTRACT_FACTS_ACCEPTED :
				return new AbstractFactsAccepted();
			default :
				return null;
		}
	}

}
