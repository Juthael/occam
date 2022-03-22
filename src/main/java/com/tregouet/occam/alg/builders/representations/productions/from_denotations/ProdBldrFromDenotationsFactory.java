package com.tregouet.occam.alg.builders.representations.productions.from_denotations;

import com.tregouet.occam.alg.builders.representations.productions.from_denotations.impl.MapTargetVarsToSourceValues;

public class ProdBldrFromDenotationsFactory {
	
	public static final ProdBldrFromDenotationsFactory INSTANCE = new ProdBldrFromDenotationsFactory();
	
	private ProdBldrFromDenotationsFactory() {
	}
	
	public IProdBuilderFromDenotations apply(ProdConstrFromDenotationsStrategy strategy) {
		switch(strategy) {
			case MAP_TARGET_VARS_TO_SOURCE_VALUES : 
				return new MapTargetVarsToSourceValues();
			default : 
				return null;
		}
	}

}
