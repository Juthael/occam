package com.tregouet.occam.alg.builders.preconcepts.denotations;

import com.tregouet.occam.alg.builders.preconcepts.denotations.impl.MaxSymbolSubsequences;

public class DenotationBuilderFactory {
	
	public static final DenotationBuilderFactory INSTANCE = new DenotationBuilderFactory();
	
	private DenotationBuilderFactory() {
	}
	
	public IDenotationBuilder apply(DenotationConstructionStrategy strategy) {
		switch(strategy) {
			case MAX_SYMBOL_SUBSEQUENCES : 
				return new MaxSymbolSubsequences();
			default :
				return null;
		}
	}

}
