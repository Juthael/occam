package com.tregouet.occam.alg.displayers.formatters.facts;

import com.tregouet.occam.alg.displayers.formatters.facts.impl.NonTrivialMaximalFacts;

public class FactDisplayerFactory {
	
	public static final FactDisplayerFactory INSTANCE = new FactDisplayerFactory();
	
	private FactDisplayerFactory() {
	}
	
	public FactDisplayer apply(FactDisplayerStrategy strategy) {
		switch (strategy) {
			case NON_TRIVIAL_MAXIMAL_FACTS : 
				return NonTrivialMaximalFacts.INSTANCE;
			default : 
				return null;
		}
	}

}
