package com.tregouet.occam.alg.builders.preconcepts.trees;

import com.tregouet.occam.alg.builders.preconcepts.trees.impl.UnidimensionalSorting;

public class PreconceptTreeBuilderFactory {
	
	public static final PreconceptTreeBuilderFactory INSTANCE = new PreconceptTreeBuilderFactory();
	
	private PreconceptTreeBuilderFactory() {
	}
	
	public IPreconceptTreeBuilder apply(PreconceptTreeConstructionStrategy strategy) {
		switch(strategy) {
			case UNIDIMENSIONAL_SORTING : 
				return new UnidimensionalSorting();
			default : 
				return null;
		}
	}

}
