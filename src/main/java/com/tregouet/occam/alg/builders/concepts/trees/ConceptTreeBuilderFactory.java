package com.tregouet.occam.alg.builders.concepts.trees;

import com.tregouet.occam.alg.builders.concepts.trees.impl.UnidimensionalSorting;

public class ConceptTreeBuilderFactory {
	
	public static final ConceptTreeBuilderFactory INSTANCE = new ConceptTreeBuilderFactory();
	
	private ConceptTreeBuilderFactory() {
	}
	
	public IConceptTreeBuilder apply(ConceptTreeConstructionStrategy strategy) {
		switch(strategy) {
			case UNIDIMENSIONAL_SORTING : 
				return new UnidimensionalSorting();
			default : 
				return null;
		}
	}

}
