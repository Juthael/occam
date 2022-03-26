package com.tregouet.occam.alg.builders.representations.concept_trees;

import com.tregouet.occam.alg.builders.representations.concept_trees.impl.UnidimensionalSorting;

public class ConceptTreeBuilderFactory {
	
	public static final ConceptTreeBuilderFactory INSTANCE = new ConceptTreeBuilderFactory();
	
	private ConceptTreeBuilderFactory() {
	}
	
	public ConceptTreeBuilder apply(ConceptTreeBuilderStrategy strategy) {
		switch(strategy) {
			case UNIDIMENSIONAL_SORTING : 
				return new UnidimensionalSorting();
			default : 
				return null;
		}
	}

}
