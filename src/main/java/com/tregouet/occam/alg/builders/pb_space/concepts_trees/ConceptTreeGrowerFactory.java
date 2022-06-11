package com.tregouet.occam.alg.builders.pb_space.concepts_trees;

import com.tregouet.occam.alg.builders.pb_space.concepts_trees.impl.IfLeafIsUniversalThenSort;

public class ConceptTreeGrowerFactory {

	public static final ConceptTreeGrowerFactory INSTANCE = new ConceptTreeGrowerFactory();

	private ConceptTreeGrowerFactory() {
	}

	public ConceptTreeGrower apply(ConceptTreeGrowerStrategy strategy) {
		switch(strategy) {
		case IF_LEAF_IS_UNIVERSAL_THEN_SORT :
			return new IfLeafIsUniversalThenSort();
		default :
			return null;
		}
	}

}
