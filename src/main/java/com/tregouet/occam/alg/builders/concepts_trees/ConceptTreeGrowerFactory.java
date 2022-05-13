package com.tregouet.occam.alg.builders.concepts_trees;

import com.tregouet.occam.alg.builders.concepts_trees.impl.IfLeafIsUniversalThenSort;

public class ConceptTreeGrowerFactory {
	
	public static final ConceptTreeGrowerFactory INSTANCE = new ConceptTreeGrowerFactory();
	
	private ConceptTreeGrowerFactory() {
	}
	
	public ConceptTreeGrower apply(ConceptTreeGrowerStrategy strategy) {
		switch(strategy) {
		case IF_LEAF_IS_UNIVERSAL_THEN_SORT : 
			return IfLeafIsUniversalThenSort.INSTANCE;
		default : 
			return null;		
		}
	}

}
