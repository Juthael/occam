package com.tregouet.occam.alg.builders.representations.concept_trees;

import com.tregouet.occam.alg.builders.representations.concept_trees.impl.IfLeafIsUniversalThenSort;

public class ConceptTreeGrowerFactory {
	
	public static final ConceptTreeGrowerFactory INSTANCE = new ConceptTreeGrowerFactory();
	
	private ConceptTreeGrowerFactory() {
	}
	
	public ConceptTreeGrower apply(ConceptTreeGrowerStrategy strategy) {
		switch(strategy) {
		case PARTITION_UNSORTED_UNIVERSALS : 
			return IfLeafIsUniversalThenSort.INSTANCE;
		default : 
			return null;		
		}
	}

}
